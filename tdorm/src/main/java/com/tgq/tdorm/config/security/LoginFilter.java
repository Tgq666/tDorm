package com.tgq.tdorm.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgq.tdorm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Security默认支持表单的登录方式，如果需要支持json格式的，需要进行重写
 * @Author tgq
 * @Date 2020/12/10 12:17
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    SessionRegistry sessionRegistry;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String loginMethod = "POST";
        if (!loginMethod.equals(request.getMethod())) {
            //登录请求，为了安全性，使用POST请求。
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        //json格式的，使用自己的方式处理,
        // 主要是获取参数方式的不同，json从输入流中读取，父类从表单参数（getPara）读取
        if(request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE))
        {
            Map<String,String> loginData = new HashMap<>(2);
            try {
                loginData = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //获取账号和密码
            String username = loginData.get(this.getUsernameParameter());
            username = username != null ? username : "";
            username = username.trim();
            String password = loginData.get(this.getPasswordParameter());
            password = password != null ? password : "";
            //存放用户的所有的认证信息，如principal：主要的身份信息，credentials：用于证明principal是正确的信息，比如密码
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            User principal = new User();
            principal.setUsername(username);
            sessionRegistry.registerNewSession(request.getSession().getId(), principal);
            return this.getAuthenticationManager().authenticate(authRequest);
        }else {
            return super.attemptAuthentication(request,response);
        }
    }
}
