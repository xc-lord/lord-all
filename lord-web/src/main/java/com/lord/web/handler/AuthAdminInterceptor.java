package com.lord.web.handler;

import com.alibaba.fastjson.JSON;
import com.lord.common.constant.WebChannel;
import com.lord.common.constant.auth.AuthValidate;
import com.lord.common.dto.mis.UserMenu;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.model.mis.MisMenu;
import com.lord.common.service.mis.MisMenuService;
import com.lord.utils.dto.Code;
import com.lord.utils.dto.Result;
import com.lord.web.utils.WebUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 功能：后台用户的权限验证拦截器
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月22日 17:30
 */
public class AuthAdminInterceptor implements HandlerInterceptor
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    private MisMenuService misMenuService;

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        //在请求处理之前进行调用（Controller方法调用之前）

        //用户登录验证
        LoginUser loginUser = UserHandler.getLoginUser(WebChannel.MIS, request, response);
        if(loginUser == null)
        {
            Result r = new Result(Code.ApiNotLogin);
            WebUtil.writeJson(response, JSON.toJSONString(r));//未登录
            return false;
        }

        //超级管理员具有所有权限
        /*if (loginUser.getSuperAdmin())
        {
            return true;//具有所有权限
        }*/

        //菜单权限验证
        String url = request.getRequestURI();
        UserMenu userMenu = misMenuService.getUserMenu(loginUser.getRoleId(), loginUser.getSuperAdmin());
        List<MisMenu> notRightMenus = userMenu.getNotRightMenus();
        for (MisMenu menu : notRightMenus)
        {
            //根据无权限菜单的服务端地址，验证当前链接是否有权限
            if(StringUtils.isEmpty(menu.getServerUrl())) continue;
            if (url.startsWith(menu.getServerUrl()))
            {
                logger.warn("url={}，缺少菜单{}{}的权限", url, menu.getId(), menu.getName());
                Result r = new Result(Code.ApiNotPermissions, "用户权限不足，缺少菜单" + menu.getId() + menu.getName() + "的权限！");//权限不足
                WebUtil.writeJson(response, JSON.toJSONString(r));
                return false;
            }
        }

        //菜单的按钮权限验证
        AuthValidate validate = ((HandlerMethod) handler).getMethodAnnotation(AuthValidate.class);
        if(validate == null) {
            return true;
        }
        Map<String, Boolean> rightMap = misMenuService.getRightMap(loginUser.getRoleId(), loginUser.getSuperAdmin());
        String rightName = validate.value().getName();
        String rightCode = validate.value().getCode();
        if(rightMap.get(rightCode) == null) {
            logger.warn("url={}，缺少{}{}的权限", url, rightName, rightCode);
            Result r = new Result(Code.ApiNotPermissions, "用户权限不足，缺少菜单" + rightName + rightCode + "的权限！");//权限不足
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

    public MisMenuService getMisMenuService()
    {
        return misMenuService;
    }

    public void setMisMenuService(MisMenuService misMenuService)
    {
        this.misMenuService = misMenuService;
    }
}
