package com.tgq.tdorm.service.dorm;

import com.tgq.tdorm.entity.Dormitory;
import com.tgq.tdorm.mapper.DormMapper;
import com.tgq.tdorm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author tgq
 * @Date 2020/12/14 23:20
 */
@Service
public class DormService {

    @Autowired
    DormMapper dormMapper;

    @Autowired
    UserMapper userMapper;

    public int createDormitory(String name){
        int lastDormNum = dormMapper.getLastDormNum();
        dormMapper.createDormitoryByName(name,String.valueOf(lastDormNum+1));
        return lastDormNum + 1;
    }

    public Dormitory findDormitoryByDormNum(String dormNum){
        return dormMapper.getDormByDormNum(dormNum);
    }

    public String getDormNumByDormNum(String dormNum){
        Dormitory dormitory = findDormitoryByDormNum(dormNum);
        if(dormitory != null){
            return dormitory.getDormName();
        }else{
            return "";
        }
    }

}
