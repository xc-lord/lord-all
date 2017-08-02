package com.lord.web.handler;

import com.alibaba.fastjson.JSON;
import com.lord.common.constant.WebChannel;
import com.lord.common.dto.user.LoginUser;
import com.lord.utils.dto.Code;
import com.lord.utils.dto.Result;
import com.lord.web.utils.WebUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能：后台用户的权限验证拦截器
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月22日 17:30
 */
public class AuthAdminInterceptor implements HandlerInterceptor
{
    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception
    {
        //在请求处理之前进行调用（Controller方法调用之前）
        LoginUser loginUser = UserHandler.getLoginUser(WebChannel.MIS, request, response);
        if(loginUser == null)
        {
            Result r = new Result(Code.ApiNotLogin);
            WebUtil.writeJson(response, JSON.toJSONString(r));
            return false;
        }
        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception
    {
        //请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception
    {
        //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
        UserHandler.remove();
    }
}
