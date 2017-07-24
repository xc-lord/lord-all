package com.lord.web.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 功能：web服务端的工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月22日 15:37
 */
public class WebUtil
{
    /**
     * 获取客户端的IP地址
     * @param request   当前请求
     * @return  IP地址
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }

    public static void write(HttpServletResponse response, String str) throws IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(str);
    }

    public static void writeJson(HttpServletResponse response, String str) throws IOException
    {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(str);
    }
}
