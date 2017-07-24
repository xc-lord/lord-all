package com.lord.web.handler;

import com.alibaba.fastjson.JSON;
import com.lord.common.constant.WebChannel;
import com.lord.common.dto.user.UserLoginOutput;
import com.lord.utils.CommonUtils;
import com.lord.utils.EncryptUtils;
import com.lord.web.config.SpringUtils;
import com.lord.web.utils.CookieUtil;
import com.lord.web.utils.WebUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    /** Redis中登录用户的Key */
    public static final String LOGIN_USER_KEY = "LOGIN_USER_%s_%s";

    /** 保存当前登录的用户 */
    private static ThreadLocal<UserLoginOutput> userHolder = new ThreadLocal<UserLoginOutput>();

    private static StringRedisTemplate stringRedisTemplate;

    /**
     * 获得当前登录的用户
     * @return 当前登录的用户
     */
    public static UserLoginOutput getLoginUser() {
        return userHolder.get();
    }

    /**
     * 设置当前登录的用户
     * @param userLoginOutput 用户
     */
    public static void setLoginUser(UserLoginOutput userLoginOutput)
    {
        userHolder.set(userLoginOutput);
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
    public static void loginSuccess(UserLoginOutput output, HttpServletRequest request, HttpServletResponse response)
    {
        if (WebChannel.APP.equals(output.getWebChannel()))
        {
            return;//如果是APP渠道，则不需要设置cookie
        }
        String token = CommonUtils.getUUID();
        output.setToken(token);
        final String key = String.format(LOGIN_USER_KEY, output.getWebChannel(), output.getUserId());
        saveToRedis(key, output);

        String sign = createSign(output, request);
        CookieUtil cookieUtil = new CookieUtil(request, response);
        cookieUtil.setCookie(output.getWebChannel() + "_USER_ID", output.getUserId() + "", CookieUtil.LONG_TIME);
        cookieUtil.setCookie(output.getWebChannel() + "_USER_NAME", output.getUsername(), CookieUtil.LONG_TIME);
        cookieUtil.setCookie(output.getWebChannel() + "_NICK_NAME", output.getNickname(), CookieUtil.LONG_TIME);
        cookieUtil.setCookie(output.getWebChannel() + "_USER_ICON", output.getIcon());
        cookieUtil.setCookie(output.getWebChannel() + "_AUTH_SIGN", sign);//用户是否登录，验证的签名，会话期间有效
        output.setToken(sign);
    }

    /**
     * 创建登录用户验证的签名
     * @param output    用户信息
     * @param request   当前请求
     * @return  签名
     */
    private static String createSign(UserLoginOutput output, HttpServletRequest request)
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
    public static UserLoginOutput getLoginUser(WebChannel channel, HttpServletRequest request, HttpServletResponse response)
    {
        if(UserHandler.getLoginUser() != null)
            return UserHandler.getLoginUser();
        CookieUtil cookieUtil = new CookieUtil(request, response);
        String cookieSign = cookieUtil.getString(channel + "_AUTH_SIGN");
        Long userId = cookieUtil.getLong(channel + "_USER_ID");
        if(StringUtils.isEmpty(cookieSign) || userId == null)
            return null;
        final String key = String.format(LOGIN_USER_KEY, channel, userId);
        String redisJson = getRedisTemplate().opsForValue().get(key);
        if(StringUtils.isEmpty(redisJson))
            return null;
        UserLoginOutput output = JSON.parseObject(redisJson, UserLoginOutput.class);
        if(output == null)
            return null;
        String sign = createSign(output, request);
        if (sign.equals(cookieSign))
        {
            long nowTime = new Date().getTime();
            long expireTime = output.getExpireTime().getTime();
            if ((expireTime - nowTime) < 120000)
            {
                //还差2分钟就到用户的Redis失效时间，重新设置用户缓存，刷新失效时间
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
        return getRedisTemplate().keys("LOGIN_USER_*");
    }

    /**
     * 保存当前登录用户的信息到Redis中
     * @param key       redis的key
     * @param output    用户信息
     */
    private static void saveToRedis(String key, UserLoginOutput output)
    {
        int timeOut = 1;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, timeOut);
        output.setExpireTime(calendar.getTime());
        getRedisTemplate().opsForValue().set(key, JSON.toJSONString(output), timeOut, TimeUnit.HOURS);
    }

    private static StringRedisTemplate getRedisTemplate() {
        if(stringRedisTemplate == null)
            stringRedisTemplate = SpringUtils.getBean(StringRedisTemplate.class);
        return stringRedisTemplate;
    }
}
