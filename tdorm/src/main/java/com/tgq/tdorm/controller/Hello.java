package com.tgq.tdorm.controller;

import com.tgq.tdorm.entity.Dormitory;
import com.tgq.tdorm.entity.Role;
import com.tgq.tdorm.entity.User;
import com.tgq.tdorm.entity.cons.AuthType;
import com.tgq.tdorm.mapper.DormMapper;
import com.tgq.tdorm.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author tgq
 * @Date 2020/12/19 16:33
 */
@RestController
public class Hello {

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    DormMapper dormMapper;
    @GetMapping("/test")
    public User test(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        User principal = new User();
        if(authentication != null){
            principal = (User) authentication.getPrincipal();
        }
        System.out.println(principal);
        return principal;
    }
}
