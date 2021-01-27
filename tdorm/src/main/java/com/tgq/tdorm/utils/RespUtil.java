package com.tgq.tdorm.utils;

import com.tgq.tdorm.entity.cons.RespCode;

/**
 * @Author tgq
 * @Date 2020/12/3 16:02
 */
public class RespUtil {

    private int code;
    private String msg;
    private Object data;

    public RespUtil(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static RespUtil success(String msg){
        return new RespUtil(RespCode.SUCCESS,msg,null);
    }

    public static RespUtil success(String msg,Object data){
        return new RespUtil(RespCode.SUCCESS,msg,data);
    }

    public static RespUtil error(String msg){
        return new RespUtil(RespCode.SYSTEM_ERROR,msg,null);
    }

    public static RespUtil error(String msg,Object data){
        return new RespUtil(RespCode.SYSTEM_ERROR,msg,data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
