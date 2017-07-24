package com.lord.common.dto.user;

import com.lord.common.constant.WebChannel;

/**
 * 功能：用户登录表单
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月22日 10:01
 */
public class UserLoginInput
{
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** 页面验证码 */
    private String pageCode;
    /** 登录的IP */
    private String ip;
    /** 登录渠道 */
    private WebChannel webChannel = WebChannel.MIS;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPageCode()
    {
        return pageCode;
    }

    public void setPageCode(String pageCode)
    {
        this.pageCode = pageCode;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public WebChannel getWebChannel()
    {
        return webChannel;
    }

    public void setWebChannel(WebChannel webChannel)
    {
        this.webChannel = webChannel;
    }
}
