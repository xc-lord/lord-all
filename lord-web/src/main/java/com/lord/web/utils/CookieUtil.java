package com.lord.web.utils;

import com.lord.utils.CommonUtils;
import com.lord.utils.constant.RegularExpression;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 功能：cookies操作类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月22日 14:54
 */
public class CookieUtil
{
	/** cookie长期有效时间，30天内有效 */
	public static final int LONG_TIME = 2592000;

	private HttpServletRequest request;

	private HttpServletResponse response;

	private String path = "/"; // 默认路径

	private String domain = null; // 域

	private Integer maxAge; // 最大有效期

	private Logger logger = LoggerFactory.getLogger(CookieUtil.class);

	public CookieUtil(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	/**
	 * 根据名称获取cookies的值
	 * @param name	cookie的名称
	 * @return	cookie的值
	 */
	public Cookie getCookie(String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (name.equals(cookies[i].getName())) {
					return cookies[i];
				}
			}
		}
		return null;
	}

	/**
	 * 设置cookie
	 * @param name		名称
	 * @param value		值
	 */
	public void setCookie(String name, String value) {
		setCookie(name, value, maxAge);
	}

	/**
	 * 设置cookie
	 * @param name		名称
	 * @param value		值
	 * @param cookieMaxAge	有效时间，单位秒
	 */
	public void setCookie(String name, String value, Integer cookieMaxAge) {
		setCookie(name, value, cookieMaxAge, path, domain);
	}

	/**
	 * 设置cookie
	 * @param name		名称
	 * @param value		值
	 * @param path		路径
	 * @param domain	域名
	 * @param cookieMaxAge	有效时间，单位秒，0为删除cookie，-1为会话有效，其他为正常时间
	 */
	public void setCookie(String name, String value, Integer cookieMaxAge, String path, String domain) {
		try
		{
			if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value))
			{
				return;
			}
			Cookie cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
			setPath(cookie, path);
			setDomain(cookie, domain);
			//如果在服务器端没有调用setMaxAge方法设置cookie的有效期，那么cookie的有效期只在一次会话过程中有效
			if(cookieMaxAge != null)
				cookie.setMaxAge(cookieMaxAge);
			response.addCookie(cookie);
		} catch (Exception e) {
			logger.error("设置cookie=" + name + "失败：" + e.getMessage(), e);
		}
	}

	private void setPath(Cookie cookie, String path)
	{
		if (StringUtils.isNotEmpty(path))
        {
            cookie.setPath(path);
        }
        else
        {
            cookie.setPath("/");
        }
	}

	private void setDomain(Cookie cookie, String domain)
	{
		if (StringUtils.isNotEmpty(domain))
        {
            cookie.setDomain(domain);
        } else {
            //获取网站的根域名
            String serverName = request.getServerName();
            String rootDomain = CommonUtils.matchedOne(serverName, RegularExpression.rootDomain);
            if(rootDomain != null)
                cookie.setDomain(rootDomain);
        }
	}

	/**
	 * 删除cookie
	 *
	 * @param name 名称
	 */
	public void removeCookie(String name) {
		try {
			Cookie cookie = new Cookie(name, "");
			setPath(cookie, path);
			setDomain(cookie, domain);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		} catch (Exception e)
		{
			logger.error("删除cookie=" + name + "失败：" + e.getMessage(), e);
		}
	}

	/**
	 * 得到COOKIE中的字符串
	 * 
	 * @param name 名称
	 * @return
	 */
	public String getString(String name) {
		Cookie cookie = getCookie(name);
		if (cookie == null)
			return null;
		String value = cookie.getValue();
		if (StringUtils.isEmpty(value))
			return null;
		try {
			value = URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 返回整数型的cookie值
	 * 
	 * @param name		名称
	 * @return 整形
	 */
	public Integer getInteger(String name)
	{
		String value = getString(name);
		if (value == null)
		{
			return null;
		}
		return Integer.parseInt(value);
	}

	/**
	 * 返回长整形的cookie值
	 *
	 * @param name		名称
	 * @return 长整形
	 */
	public Long getLong(String name)
	{
		String value = getString(name);
		if (value == null)
		{
			return null;
		}
		return Long.parseLong(value);
	}

	/**
	 * 返回浮点型的cookie值
	 * 
	 * @param name		名称
	 * @return 浮点数
	 */
	public Double getDouble(String name) {
		String value = getString(name);
		if (value == null)
		{
			return null;
		}
		return Double.parseDouble(value);
	}
	
	/**
	 * 设置默认的PATH
	 * 
	 * @param path	路径
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 设置默认的DOMAIN
	 * 
	 * @param domain 域名
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * 设置默认的MAX_AGE
	 * 
	 * @param maxAge 有效期
	 */
	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}
}
