package com.tgq.tdorm.mapper;

import com.tgq.tdorm.entity.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author tgq
 * @Date 2020/12/18 15:50
 */
public interface RoleMapper {

    /**
     * 根据角色的姓名批量查询用户的id
     * @param nameDesc 角色的描述（名称）
     * @return 角色id
     */
    Integer selectRoleIdByDesc(String nameDesc);

    /**
     * 为用户添加角色
     * @param uid 用户id
     * @param rid 角色id
     */
    @Insert("insert into user_role (uid,rid) values (#{uid},#{rid})")
    void insertUserRole(Integer uid,Integer rid);

    List<Role> selectAllRole();
}
