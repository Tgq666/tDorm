package com.tgq.tdorm.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author tgq
 * @Date 2021/2/21 15:19
 */
public class IPUtils {
    public static String getIpAddr(HttpServletRequest request) {
        String ip=request.getHeader("x-forwarded-for");
        if(null!=ip && ip.length()!=0 && !"unknown".equalsIgnoreCase(ip)) {
            if(ip.indexOf(",")!=-1) {
                ip=ip.split(",")[0];
            }
        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)) {
            ip=request.getHeader("Proxy-Client-Ip");
        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)) {
            ip=request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)) {
            ip=request.getHeader("HTTP_CLIENT_IP");
        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)) {
            ip=request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)) {
            ip=request.getHeader("X-Real-IP");
        }
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)) {
            ip=request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;

    }
}
