package com.tgq.tdorm.controller;

import com.tgq.tdorm.mapper.DormMapper;
import com.tgq.tdorm.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
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


}
