package com.tgq.tdorm.controller.consume;

import com.tgq.tdorm.annotation.Log;
import com.tgq.tdorm.entity.Consume;
import com.tgq.tdorm.entity.pojo.Bill;
import com.tgq.tdorm.service.consume.ConsumeService;
import com.tgq.tdorm.utils.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author tgq
 * @Date 2020/12/29 16:42
 */
@RestController
public class ConsumeInfoController {

    @Autowired
    ConsumeService consumeService;

    @Log("未支付室费查询")
    @GetMapping("/unSdConsumeInfo")
    public RespUtil settledConsumeInfo(String dormNum){
        List<Consume> dormInfo = consumeService.getUnSettledConsumeInfo(dormNum);
        return RespUtil.success("查询成功",dormInfo);
    }

    @Log("已支付室费查询")
    @GetMapping("/sdConsumeInfo")
    public RespUtil unSettledConsumeInfo(String dormNum){
        List<Consume> dormInfo = consumeService.getSettledConsumeInfo(dormNum);
        return RespUtil.success("查询成功",dormInfo);
    }

    @Log("添加消费")
    @PostMapping("/addConsume")
    public RespUtil addConsume(Consume consume){
        consumeService.addConsume(consume);
        return RespUtil.success("添加成功");
    }

    @Log("删除消费")
    @PostMapping("/deleteConsume")
    public RespUtil deleteConsume(Integer id){
        consumeService.deletedConsume(id);
        return RespUtil.success("删除成功");
    }

    @Log("账单结算")
    @PostMapping("/confirmConsume")
    public RespUtil confirmConsume(@RequestBody List<Bill> confirmList){
        try {
            consumeService.confirmConsume(confirmList);
        }catch (Exception e){
            e.printStackTrace();
            return RespUtil.error("结算失败");
        }
        return RespUtil.success("结算成功");
    }
}
