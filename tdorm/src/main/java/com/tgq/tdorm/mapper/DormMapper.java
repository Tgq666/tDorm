package com.tgq.tdorm.mapper;

import com.tgq.tdorm.entity.Dormitory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Author tgq
 */
public interface DormMapper {
    /**
     * 创建寝室号码的时候需要在最新的寝室号码上+1，此为获取最新的寝室号码
     * @return 返回最新的寝室号码
     */
    @Select("SELECT dorm_num FROM dormitory ORDER BY dorm_num DESC LIMIT 0,1")
    int getLastDormNum();

    /**
     * 获取寝室的名称
     * @param dormNum 寝室号码
     * @return 寝室名称
     */
    @Select("select dorm_name from dormitory where dorm_num = #{dormNum}")
    String getDormNameByDormNum(String dormNum);

    /**
     * 根据寝室号查询寝室信息
     * @param dormNum 寝室号
     * @return 寝室信息
     */
    Dormitory getDormByDormNum(String dormNum);

    /**
     * 输入寝室名称，创建寝室，
     * @param dormAdmin 管理员名称
     * @param did 更新的寝室id
     */
    @Update("update dormitory set dorm_admin = #{dormAdmin} where id = #{did}")
    void updateDormAdmin(String dormAdmin,Integer did);

    /**
     * 输入寝室名称，创建寝室，
     * @param dormName 寝室名称
     * @param dormNum 寝室号码
     */
    @Insert("INSERT INTO dormitory (dorm_name,dorm_num,dorm_admin,dorm_desc,CREATE_time,update_time,is_deleted)" +
            "VALUES(#{dormName},#{dormNum},NULL,'666666',NOW(),NOW(),0)")
    void createDormitoryByName(String dormName,String dormNum);


}
