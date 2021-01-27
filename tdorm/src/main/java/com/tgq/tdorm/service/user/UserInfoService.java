package com.tgq.tdorm.service.user;

import com.tgq.tdorm.entity.User;
import com.tgq.tdorm.entity.Username;
import com.tgq.tdorm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author tgq
 * @Date 2020/12/13 15:38
 */
@Service
public class UserInfoService {

    @Autowired
    UserMapper userMapper;

    public User getUserByUserName(String username){
        return userMapper.selectUserByUsername(username);
    }

    public List<Username> getDormUserByDormNum(String dormNum){
        List<User> users = userMapper.selectDormUser(dormNum);
        List<Username> names = new LinkedList<>();
        for (User user : users) {
            Username curUserName = new Username();
            curUserName.setId(user.getId());
            curUserName.setName(user.getName());
            names.add(curUserName);
        }
        return names;
    }
}
