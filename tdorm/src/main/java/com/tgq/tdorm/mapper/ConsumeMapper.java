package com.tgq.tdorm.mapper;

import com.tgq.tdorm.entity.Consume;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author tgq
 * @Date 2020/12/29 16:09
 */
public interface ConsumeMapper {

    /**
     * 查询某寝室的消费记录
     * @param dormNum 寝室号
     * @param isDeleted 是否已经逻辑删除了
     * @return 返回该寝室所有的消费记录
     */
    List<Consume> getConsumeByDormNum(String dormNum,Integer isDeleted);

    /**
     * 获取消费信息中，已经结算的姓名
     * @param id 消费id
     * @return 返回已经结算的名字
     */
    @Select("SELECT settled_name FROM consume WHERE id = #{id}")
    String getSettledConfirm(Integer id);

    /**
     * 添加消费
     * @param consume 消费信息
     */
    void addConsume(Consume consume);

    /**
     * 删除消费信息
     * @param id 删除的消费信息的id号
     */
    @Update("UPDATE consume SET is_deleted = 1,update_time = NOW() WHERE id = #{id}")
    void deleteConsume(Integer id);

    /**
     * 更新消费信息的结算名字
     * @param id 消费的Id
     * @param settledName 结算的名字
     */
    @Update("UPDATE consume SET settled_name = #{settledName},update_time = NOW() WHERE id = #{id}")
    void updateConfirmConsume(Integer id,String settledName);
}
