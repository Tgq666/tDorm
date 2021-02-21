package com.tgq.tdorm.service.contract;

import com.tgq.tdorm.entity.Contract;
import com.tgq.tdorm.mapper.ContractMapper;
import com.tgq.tdorm.utils.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

/**
 * @Author tgq
 * @Date 2021/2/12 0:56
 */
@Service
public class ContractService {

    @Autowired
    ContractMapper contractMapper;
    /**
     * 添加寝室合约
     * @param contract 添加的合约信息
     */
    public void addContract(Contract contract){
        contract.setUpdateTime(new Date());
        contractMapper.addContract(contract);
    }

    /**
     * 获取寝室合约信息
     * @param dormNum 寝室号
     * @return 寝室合约
     */
    public List<Contract> getContract(String dormNum){
        return contractMapper.getContract(dormNum);
    }

    /**
     * 删除寝室合约信息
     * @param id 合约id
     */
    public void deleteContract(Integer id){
        contractMapper.deleteContract(id);
    }
}
