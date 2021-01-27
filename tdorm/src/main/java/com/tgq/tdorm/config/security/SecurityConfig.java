package com.tgq.tdorm.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgq.tdorm.entity.User;
import com.tgq.tdorm.entity.cons.ResponseMessage;
import com.tgq.tdorm.entity.cons.ConstantUtil;
import com.tgq.tdorm.utils.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.PrintWriter;

/**
 * @Author tgq
 * @Date 2020/12/3 20:22
 * @EnableWebSecurity :开启Security
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //使用自己的UserDetails验证
        auth.userDetailsService(userService);
    }

    /**
     * @return 向Spring Security中注入LoginFiter
     */
    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        //前后端不分离，使用loginProcessingUrl，跳转成功页面
        //前后端分离，使用successHandler，处理后返回给前端，让前端跳转
        loginFilter.setAuthenticationSuccessHandler((req, resp, auth) -> {
            resp.setContentType("application/json;charset=utf-8");
            User user = (User) auth.getPrincipal();
            PrintWriter out = resp.getWriter();
            RespUtil success = RespUtil.success(ResponseMessage.LOGIN_SUCCESS, user);
            out.write(new ObjectMapper().writeValueAsString(success));
            out.flush();
            out.close();
        });
        //登录失败的处理
        loginFilter.setAuthenticationFailureHandler((req, resp, auth) -> {
            resp.setContentType("application/json;charset=utf-8");
            RespUtil error = RespUtil.error(ResponseMessage.LOGIN_FAILURE);
            PrintWriter out = resp.getWriter();
            out.write(new ObjectMapper().writeValueAsString(error));
            out.flush();
            out.close();
        });
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setFilterProcessesUrl("/doLogin");
        ConcurrentSessionControlAuthenticationStrategy sessionStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
        sessionStrategy.setMaximumSessions(1);
        //设置第一次请求进行验证时调用的rememberService
        loginFilter.setRememberMeServices(persistentTokenBasedRememberMeServices());
        loginFilter.setSessionAuthenticationStrategy(sessionStrategy);
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        return loginFilter;
    }

    @Override
    public void configure(WebSecurity web) {
        //取消对静态资源的拦截
        web.ignoring().antMatchers("/css/**", "/js/**", "/index.html", "/img/**", "/fonts/**", "/favicon.ico");
        //注册页面相关的不需要拦截
        web.ignoring().antMatchers("/userExit", "/register", "/test", "/dormExit","/createDorm");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //配置不需要登陆验证
        http.authorizeRequests()
                //任何请求都需要进行授权
                .anyRequest().authenticated()
                .and()
                .rememberMe()
//              .userDetailsService(securityUserService())
                .key("uniqueAndSecret")
                //设置第二次请求调用的rememberService 验证cookies 默认是tokenBasedRememberServices
                .rememberMeServices(persistentTokenBasedRememberMeServices())
                .and()
                .logout()
                //前后端分离的注销成功处理
                .logoutSuccessHandler((req, resp, auth) -> {
                    resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    RespUtil success = RespUtil.success(ResponseMessage.LOGOUT_SUCCESS);
                    Cookie cookie = new Cookie("tDormUser",null);
                    cookie.setMaxAge(0); // expires in 7 days
                    cookie.setDomain(ConstantUtil.DOMIN);
                    resp.addCookie(cookie);
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(success));
                    out.flush();
                    out.close();
                })
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, auth) -> {
                    resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    RespUtil error = RespUtil.error(ResponseMessage.NO_PERMISSION);
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(error));
                    out.flush();
                    out.close();
                });
        //用户登录过期
        http.addFilterAt(new ConcurrentSessionFilter(sessionRegistry(), event -> {
            HttpServletResponse resp = event.getResponse();
            resp.setContentType("application/json;charset=utf-8");
            resp.setStatus(401);
            PrintWriter out = resp.getWriter();
            out.write(new ObjectMapper().writeValueAsString(RespUtil.error("登录过期，请重新登录")));
            out.flush();
            out.close();
        }), ConcurrentSessionFilter.class);
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 密码加密
     *
     * @return 返回加密的密码
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SessionRegistryImpl sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean("persistentTokenBasedRememberMeServices")
    public PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices() {
        PersistentTokenBasedRememberMeServices rememberMeServices =
                new PersistentTokenBasedRememberMeServices("uniqueAndSecret", userService, tokenRepository());
        //设置checkbox的参数名为rememberMe（默认为remember-me），注意如果是ajax,axios请求，参数名不是checkbox的name而是在ajax的data里
        rememberMeServices.setParameter("rememberMe");
        rememberMeServices.setCookieName("tDormUser");
        // 设置cookie过期时间为一年
        rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 365);
        return rememberMeServices;
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        jdbcTokenRepositoryImpl.setDataSource(dataSource);
        return jdbcTokenRepositoryImpl;
    }
}
