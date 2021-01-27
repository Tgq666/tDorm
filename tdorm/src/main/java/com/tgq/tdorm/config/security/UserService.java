package com.tgq.tdorm.config.security;

import com.tgq.tdorm.entity.User;
import com.tgq.tdorm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author tgq
 * @Date 2020/12/4 15:52
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = mapper.selectUserRoleByUsername(s);
        if(null == user){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }
}
