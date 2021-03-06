package com.tgq.tdorm.service.consume;

import com.tgq.tdorm.entity.Consume;
import com.tgq.tdorm.entity.User;
import com.tgq.tdorm.entity.pojo.Bill;
import com.tgq.tdorm.mapper.ConsumeMapper;
import com.tgq.tdorm.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * @Author tgq
 * @Date 2020/12/29 16:43
 */
@Service
public class ConsumeService {

    @Autowired
    ConsumeMapper consumeMapper;

    @Autowired
    RedisService redisService;

    public List<Consume> getSettledConsumeInfo(String dormNum) {
        return getConsumeInfoFromRedis(dormNum, true,0);
    }

    public List<Consume> getUnSettledConsumeInfo(String dormNum) {
        return getConsumeInfoFromRedis(dormNum, false,0);
    }

    public List<Consume> getDeletedConsumeInfo(String dormNum) {
        return getConsumeInfoFromRedis(dormNum, false,1);
    }

    private List<Consume> getConsumeInfoFromRedis(String dormNum, boolean settled,Integer isDeleted) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String key = isDeleted == 1 ? "deleted" : settled ? "settled" : "unsettled" +principal.getId();
        List<Consume> info = (List)redisService.lGet(key);
        //从redis中查询，如果redis挂了或者在redis中没有数据，则从数据库中查询
        if(info == null || info.size() == 0){
            info = getConsumeInfo(dormNum, settled, isDeleted);
            if(info.size() > 0){
                redisService.lSet(key,(List)info,60*60);
            }
        }
        return  info;
    }

    /**
     * 获取消费信息
     * @param dormNum 对应寝室的消费信息
     * @param settled 是否已经结清
     * @return 消费信息
     */
    private List<Consume> getConsumeInfo(String dormNum, boolean settled,Integer isDeleted) {
        List<Consume> consume = consumeMapper.getConsumeByDormNum(dormNum,isDeleted);
        User curUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //获取当前用户的姓名
        String name = String.valueOf(curUser.getName());
        int i = 0;
        //未结清的列表
        List<Consume> unSettledList = new LinkedList<>();
        //已结清的列表
        List<Consume> settledList = new LinkedList<>();
        //筛选寝室所有消费信息中，需要返回的信息
        while (i < consume.size()) {
            //当前消费信息
            Consume curConsume = consume.get(i);
            //支付人的姓名
            String payName = curConsume.getPayName();
            //已结清的姓名
            List<String> tempSettledNameList = Arrays.asList(curConsume.getSettledName().split(","));
            List<String> settledNameList = new LinkedList<>(tempSettledNameList);
            //已消费的姓名
            List<String> tempConsumeNameList = Arrays.asList(curConsume.getConsumeName().split(","));
            List<String> consumeNameList = new LinkedList<>(tempConsumeNameList);
            //如果支付人没有消费，暂时把他添加进消费人中，从而能对比两边的大小
            if (!consumeNameList.contains(payName)) {
                consumeNameList.add(payName);
            }
            //如果大小相等，说明都结清了，反之还有人没有结清
            if (settledNameList.size() != consumeNameList.size()) {
                //当没有结清的情况下，自己是支付人或者是消费人，且是自己没有结清，就需要返回。
                boolean unSettled = (consumeNameList.contains(name) && !settledNameList.contains(name)) || name.equals(payName);
                if (unSettled) {
                    //添加进未结清列表
                    unSettledList.add(curConsume);
                }else if(consumeNameList.contains(name) && settledNameList.contains(name)){
                    //部分结清的情况下，自己可能是结清的一人，如果我当前在消费列表中，且我已经结清了，则添加进结清列表
                    settledList.add(curConsume);
                }
            } else if (settledNameList.contains(name)) {
                //都已经结清的情况下,添加和我相关的名字
                settledList.add(curConsume);
            }
            //只返回两种：没有全部付清的情况下：1.自己是支付人 2.自己是消费人
            i++;
        }
        return settled ? settledList : unSettledList;
    }

    private boolean delConsumeFromRedis(){
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = String.valueOf(principal.getId());
        return redisService.del(id);
    }

    public boolean addConsume(Consume consume) {
        if(delConsumeFromRedis()){
            //先删后写
            consume.setUpdateTime(new Date());
            consumeMapper.addConsume(consume);
            return true;
        }else {
            return false;
        }
    }

    public boolean deletedConsume(Integer id){
        if(delConsumeFromRedis()){
            //先删后写
            consumeMapper.deleteConsume(id);
            return true;
        }else {
            return false;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean confirmConsume(List<Bill> bills){
        if(delConsumeFromRedis()){
            //先删后写
            for (Bill bill : bills) {
                Integer id = bill.getId();
                String name = bill.getSettledName();
                String settledName = consumeMapper.getSettledConfirm(id);
                settledName = settledName + "," + name;
                consumeMapper.updateConfirmConsume(id, settledName);
            }
            return true;
        }else {
            return false;
        }
    }
}
