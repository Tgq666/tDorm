package com.tgq.tdorm.service.user;

import com.tgq.tdorm.entity.Dormitory;
import com.tgq.tdorm.entity.User;
import com.tgq.tdorm.entity.cons.AuthType;
import com.tgq.tdorm.mapper.DormMapper;
import com.tgq.tdorm.mapper.RoleMapper;
import com.tgq.tdorm.mapper.UserMapper;
import com.tgq.tdorm.utils.BCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author tgq
 * @Date 2020/12/18 15:10
 */
@Service
public class UserModifyService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    DormMapper dormMapper;

    /**
     * @param user 新增用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void register(User user) {
        //密码加密
        String encodePwd = BCryptUtil.encode(user.getPassword());
        user.setPassword(encodePwd);
        //新增用户
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insertUser(user);
        Integer uid = userMapper.selectUserIdByUsername(user.getUsername());
        //为用户分配角色
        //查询寝室信息
        Dormitory dorm = dormMapper.getDormByDormNum(user.getDormNum());
        if (dorm.getDormAdmin() == null) {
            //当前寝室还没有人加入，默认第一个人为寝室长
            dormMapper.updateDormAdmin(user.getName(), dorm.getId());
            //添加寝室长角色
            Integer rid = roleMapper.selectRoleIdByDesc(AuthType.ROLE_manager);
            roleMapper.insertUserRole(uid, rid);
        }
        //添加室员角色
        Integer rid = roleMapper.selectRoleIdByDesc(AuthType.ROLE_normal);
        roleMapper.insertUserRole(uid, rid);
    }
}
