package com.tgq.tdorm.mapper;

import com.tgq.tdorm.entity.Contract;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * @Author tgq
 * @Date 2021/2/12 0:58
 */
public interface ContractMapper {

    /**
     * 添加寝室合约
     * @param contract 添加的合约信息
     */
    void addContract(Contract contract);

    /**
     * 获取寝室合约信息
     * @param dormNum 寝室号
     * @return 寝室合约
     */
    List<Contract> getContract(String dormNum);

    /**
     * 删除寝室合约信息
     * @param id 合约id号
     */
    @Delete("update contract set is_deleted = 1 where id = #{id}")
    void deleteContract(Integer id);
}
