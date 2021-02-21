package com.tgq.tdorm.controller.contract;

import com.tgq.tdorm.annotation.Log;
import com.tgq.tdorm.entity.Contract;
import com.tgq.tdorm.service.contract.ContractService;
import com.tgq.tdorm.utils.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author tgq
 * @Date 2021/2/12 0:50
 */
@RestController
public class ContractController {

    @Autowired
    ContractService contractService;

    /**
     * 添加寝室合约
     * @param contract 添加的合约信息
     * @return 处理信息
     */
    @Log("添加合约")
    @PostMapping("/addContract")
    public RespUtil addContract(Contract contract){
        try {
            contractService.addContract(contract);
            return RespUtil.success();
        }catch (Exception e){
            e.printStackTrace();
            return RespUtil.error();
        }
    }

    /**
     * 获取寝室合约信息
     * @param dormNum 寝室号
     * @return 寝室合约
     */
    @GetMapping("/getContract")
    public RespUtil getContract(String dormNum){
        try {
            List<Contract> contract = contractService.getContract(dormNum);
            return RespUtil.success(contract);
        }catch (Exception e){
            e.printStackTrace();
            return RespUtil.error();
        }
    }

    /**
     * 删除寝室合约信息
     * @param id 合约id
     * @return 处理信息
     */
    @PostMapping("/deleteContract")
    public RespUtil deleteContract(Integer id){
        try {
            contractService.deleteContract(id);
            return RespUtil.success();
        }catch (Exception e){
            e.printStackTrace();
            return RespUtil.error();
        }
    }
}
