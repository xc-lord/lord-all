package com.lord.common.dto.user;

import com.lord.common.constant.WebChannel;

import java.util.Date;

/**
 * 功能：用户登录响应信息
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月22日 10:01
 */
public class LoginUser
{
    /** 用户Id */
    private Long userId;
    /** 用户名 */
    private String username;
    /** 用户头像 */
    private String icon;
    /** 用户昵称 */
    private String nickname;
    /** 性别：1男，2女 */
    private Integer sex;
    /** 用户等级 */
    private Integer level;
    /** 用户手机号 */
    private String phone;
    /** 用户邮箱 */
    private String email;
    /** 登录时间 */
    private Date loginTime;
    /** 失效时间 */
    private Date expireTime;
    /** 登录的IP */
    private String ip;
    /** 登录渠道 */
    private WebChannel webChannel;
    /** 证书 */
    private String token;
    /** 角色Id */
    private Long roleId;
    /** 是否超级管理员 */
    private Boolean superAdmin;

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public Integer getSex()
    {
        return sex;
    }

    public void setSex(Integer sex)
    {
        this.sex = sex;
    }

    public Integer getLevel()
    {
        return level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Date getLoginTime()
    {
        return loginTime;
    }

    public void setLoginTime(Date loginTime)
    {
        this.loginTime = loginTime;
    }

    public Date getExpireTime()
    {
        return expireTime;
    }

    public void setExpireTime(Date expireTime)
    {
        this.expireTime = expireTime;
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

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    public Boolean getSuperAdmin()
    {
        return superAdmin;
    }

    public void setSuperAdmin(Boolean superAdmin)
    {
        this.superAdmin = superAdmin;
    }
}
