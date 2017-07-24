package com.lord.utils.constant;

/**
 * 功能：正则表达式
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月24日 11:48
 */
public class RegularExpression
{
    /** 数字 */
    public static final String number = "^[0-9]*$";
    /** 汉字 */
    public static final String chinese = "^[\\u4e00-\\u9fa5]{0,}$";
    /** 空白行 */
    public static final String blank = "\\n\\s*\\r";
    /** 手机 */
    public static final String phone = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
    /** 邮箱 */
    public static final String email = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /** 域名 */
    public static final String domain = "[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(/.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+/.?";
    /** 根域名 */
    public static final String rootDomain = "(\\w*\\.(com.cn|com|net.cn|net|org.cn|org|gov.cn|gov|cn|mobi|me|info|name|biz|cc|tv|asia|hk|网络|公司|中国)).*$";
}
