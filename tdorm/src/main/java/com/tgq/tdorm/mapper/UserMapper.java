package com.tgq.tdorm.mapper;
import com.tgq.tdorm.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author tgq
 * @Date 2020/12/3 16:40
 */
public interface UserMapper {

    /**
     * 使用用户名查询用户
     * @param username 用户名
     * @return 返回用户
     */
    User selectUserByUsername(String username);

    /**
     * 根据用户名查询用户Id
     * @param username 用户名
     * @return 用户id
     */
    @Select("select id from user where username = #{username}")
    Integer selectUserIdByUsername(String username);

    /**
     * 查询用户
     * @return 返回所有的用户
     */
    List<User> findAllUser();

    /**
     * 查询带有角色信息的用户
     * @param username 用户名
     * @return 返回用户以及相应的角色
     */
    User selectUserRoleByUsername(String username);

    /**
     * 用户注册
     * @param user 注册的用户信息
     */
    void insertUser(User user);

    /**
     * 根据寝室号查询寝室用户
     * @param dormNum 寝室号
     * @return 返回所有的用户信息
     */
    @Select("SELECT id,NAME FROM USER WHERE dorm_num = #{dormNum}")
    List<User> selectDormUser(String dormNum);

}
