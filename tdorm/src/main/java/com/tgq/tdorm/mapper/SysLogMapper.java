package com.tgq.tdorm.mapper;

import com.tgq.tdorm.entity.SysLog;

/**
 * @Author tgq
 * @Date 2021/2/21 15:10
 */
public interface SysLogMapper {
    /**
     * 保存controller层的日志信息到数据库
     * @param sysLog 日志信息
     */
    void saveLog(SysLog sysLog);
}
