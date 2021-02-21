package com.tgq.tdorm.controller.dorm;

import com.tgq.tdorm.annotation.Log;
import com.tgq.tdorm.service.dorm.DormService;
import com.tgq.tdorm.utils.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author tgq
 * @Date 2020/12/13 17:25
 */
@RestController
public class DormModifyController {

    @Autowired
    DormService dormService;

    @Log("创建寝室")
    @PostMapping("/createDorm")
    public RespUtil createDorm(String dormName){
        int dormNum = dormService.createDormitory(dormName);
        return RespUtil.success("创建寝室成功",dormNum);
    }
}
