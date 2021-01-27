package com.tgq.tdorm.controller.user;

import com.tgq.tdorm.entity.User;
import com.tgq.tdorm.entity.Username;
import com.tgq.tdorm.service.user.UserInfoService;
import com.tgq.tdorm.utils.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

/**
 * 获取用户的各种基本信息
 * @Author tgq
 * @Date 2020/12/12 17:52
 */
@RestController
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @GetMapping("/userExit")
    public RespUtil userExit(String username){
        return userInfoService.getUserByUserName(username) == null ?
                RespUtil.error("用户不存在",false) :RespUtil.success("用户存在",true);
    }

    @GetMapping("/dormUser")
    public RespUtil getDormUser(String dormNum){
        List<Username> dormUser = userInfoService.getDormUserByDormNum(dormNum);
        return dormUser == null ? RespUtil.error("用户不存在",null) : RespUtil.success("查询成功",dormUser);
    }
}
