package com.lord.web.handler;

import com.alibaba.fastjson.JSON;
import com.lord.common.constant.WebChannel;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.service.RedisService;
import com.lord.utils.CommonUtils;
import com.lord.utils.EncryptUtils;
import com.lord.web.config.SpringUtils;
import com.lord.web.utils.CookieUtil;
import com.lord.web.utils.WebUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 功能：用户的登录授权和验证是否登录处理
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月22日 14:54
 */
public class UserHandler
{
    /** 保存当前登录的用户 */
    private static ThreadLocal<LoginUser> userHolder = new ThreadLocal<LoginUser>();

    private static RedisService redisService;

    /**
     * 获得当前登录的用户
     * @return 当前登录的用户
     */
    public static LoginUser getLoginUser() {
        return userHolder.get();
    }

    /**
     * 设置当前登录的用户
     * @param loginUser 用户
     */
    public static void setLoginUser(LoginUser loginUser)
    {
        userHolder.set(loginUser);
    }

    /**
     * 清空登录的用户信息
     */
    public static void clean() {
        userHolder.set(null);
    }

    /**
     * 删除登录的用户信息
     */
    public static void remove() {
        userHolder.remove();
    }

    /**
     * 登录成功后的回调，设置登录权限相关的信息，如Cookie和证书
     * @param output    用户
     * @param request   请求
     * @param response  响应
     */
    public static void loginSuccess(LoginUser output, HttpServletRequest request, HttpServletResponse response)
    {
        if (WebChannel.APP.equals(output.getWebChannel()))
        {
            return;//如果是APP渠道，则不需要设置cookie
        }
        String token = CommonUtils.getUUID();
        output.setToken(token);
        final String sessionKey = CommonUtils.getUUID();
        saveToRedis(sessionKey, output);

        String sign = createSign(output, request);
        CookieUtil cookieUtil = new CookieUtil(request, response);
        cookieUtil.setCookie(output.getWebChannel() + "_USER_ID", output.getUserId() + "", CookieUtil.LONG_TIME);
        cookieUtil.setCookie(output.getWebChannel() + "_USER_NAME", output.getUsername(), CookieUtil.LONG_TIME);
        cookieUtil.setCookie(output.getWebChannel() + "_NICK_NAME", output.getNickname(), CookieUtil.LONG_TIME);
        cookieUtil.setCookie(output.getWebChannel() + "_USER_ICON", output.getIcon());
        cookieUtil.setCookie(output.getWebChannel() + "_SESSION_KEY", sessionKey);
        cookieUtil.setCookie(output.getWebChannel() + "_AUTH_SIGN", sign);//用户是否登录，验证的签名，会话期间有效
        output.setToken(sign);
    }

    /**
     * 用户注销登录
     * @param channel   登录渠道
     * @param request   请求
     * @param response  响应
     */
    public static void logout(WebChannel channel, HttpServletRequest request, HttpServletResponse response)
    {
        CookieUtil cookieUtil = new CookieUtil(request, response);
        Long userId = cookieUtil.getLong(channel + "_USER_ID");
        final String key = cookieUtil.getString(channel + "_SESSION_KEY");
        if (userId != null)
        {
            getRedisService().delete(key);//删缓存中的key
        }
        //删除cookie中的用户信息
        cookieUtil.removeCookie(channel + "_USER_ID");
        cookieUtil.removeCookie(channel + "_USER_NAME");
        cookieUtil.removeCookie(channel + "_NICK_NAME");
        cookieUtil.removeCookie(channel + "_USER_ICON");
        cookieUtil.removeCookie(channel + "_AUTH_SIGN");
        cookieUtil.removeCookie(channel + "_SESSION_KEY");
        try
        {
            response.sendRedirect("/mis/login.html");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 创建登录用户验证的签名
     * @param output    用户信息
     * @param request   当前请求
     * @return  签名
     */
    private static String createSign(LoginUser output, HttpServletRequest request)
    {
        //用户换浏览器或者IP变更时，都需要重新登录，另外使用浏览器的F12功能切换到手机模式也需要重新登录
        String ip = WebUtil.getIP(request);
        String userAgent = request.getHeader("User-Agent");
        String str = "";
        str += "loginChannel=" + output.getWebChannel() + "&";
        str += "userId=" + output.getUserId() + "&";
        str += "token=" + output.getToken() + "&";
        str += "userAgent=" + userAgent + "&";
        str += "ip=" + ip + "&";
        //System.out.println("签名的字符串：" + str);
        return EncryptUtils.md5Encode(str);
    }

    /**
     * 获得当前登录的用户，并保存到对应线程的ThreadLocal中
     * @param channel   登录渠道
     * @param request   请求
     * @param response  响应
     * @return  当前登录的用户，为空则未登录
     */
    public static LoginUser getLoginUser(WebChannel channel, HttpServletRequest request, HttpServletResponse response)
    {
        if(UserHandler.getLoginUser() != null)
            return UserHandler.getLoginUser();
        CookieUtil cookieUtil = new CookieUtil(request, response);
        String cookieSign = cookieUtil.getString(channel + "_AUTH_SIGN");
        Long userId = cookieUtil.getLong(channel + "_USER_ID");
        if(StringUtils.isEmpty(cookieSign) || userId == null)
            return null;
        final String key = cookieUtil.getString(channel + "_SESSION_KEY");
        String redisJson = getRedisService().get(key);
        if(StringUtils.isEmpty(redisJson))
            return null;
        LoginUser output = JSON.parseObject(redisJson, LoginUser.class);
        if(output == null)
            return null;
        String sign = createSign(output, request);
        if (sign.equals(cookieSign))
        {
            if (CommonUtils.genRandom(1, 6) == 3)
            {
                //随机数刚好为3时，重新设置用户缓存，刷新失效时间，按概率每请求5次会刷新一次缓存
                saveToRedis(key, output);
            }
            output.setToken(null);//获取用户时，不获取用户证书
            UserHandler.clean();
            UserHandler.setLoginUser(output);//保存到当前请求中
            return output;
        }
        return null;
    }

    public static Set<String> onlineUser()
    {
        return getRedisService().keys("LOGIN_USER_*");
    }

    /**
     * 保存当前登录用户的信息到Redis中
     * @param key       redis的key
     * @param output    用户信息
     */
    private static void saveToRedis(String key, LoginUser output)
    {
        int timeOut = 1;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, timeOut);
        output.setExpireTime(calendar.getTime());
        getRedisService().set(key, output, timeOut, TimeUnit.HOURS);
    }

    private static RedisService getRedisService() {
        if(redisService == null)
            redisService = SpringUtils.getBean(RedisService.class);
        return redisService;
    }

}
