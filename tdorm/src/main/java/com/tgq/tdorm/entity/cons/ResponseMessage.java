package com.tgq.tdorm.entity.cons;

/**
 * @Author tgq
 */
public interface ResponseMessage {
    String LOGIN_SUCCESS = "登录成功";
    String LOGIN_FAILURE = "登录失败，用户名或密码错误！";
    String NO_PERMISSION = "您没有权限访问";
    String LOGOUT_SUCCESS = "注销成功";
}
