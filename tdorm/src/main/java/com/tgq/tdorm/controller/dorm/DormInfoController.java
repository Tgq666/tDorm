package com.tgq.tdorm.controller.dorm;

import com.tgq.tdorm.entity.Dormitory;
import com.tgq.tdorm.service.dorm.DormService;
import com.tgq.tdorm.utils.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author tgq
 * @Date 2020/12/29 16:36
 */
@RestController
public class DormInfoController {

    @Autowired
    DormService dormService;

    @GetMapping("/dormExit")
    public RespUtil dormExit(String dormNum){
        Dormitory dormitory = dormService.findDormitoryByDormNum(dormNum);
        if(dormitory != null){
            return RespUtil.success("寝室号存在，可以加入",true);
        }else {
            return RespUtil.error("寝室号不存在",false);
        }
    }

    @GetMapping("/dormName")
    public RespUtil getDormNum(String dormNum){
        String dormName = dormService.getDormNumByDormNum(dormNum);
        return RespUtil.success("查询成功",dormName);
    }

}
