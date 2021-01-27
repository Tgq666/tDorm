package com.tgq.tdorm.controller.user;

import com.tgq.tdorm.entity.User;
import com.tgq.tdorm.service.user.UserModifyService;
import com.tgq.tdorm.utils.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author tgq
 * @Date 2020/12/16 14:51
 */
@RestController
public class UserModifyController {

    @Autowired
    UserModifyService userModifyService;

    @PostMapping("/register")
    public RespUtil register(User user){
        userModifyService.register(user);
        return RespUtil.success("用户注册成功");
    }
}
