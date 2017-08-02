package com.lord.common.constant.auth;

import java.lang.annotation.*;

/**
 * 功能：自定义权限验证的注解
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月02日 19:43
 */
@Documented
@Target({ElementType.METHOD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthValidate
{
    AuthCode value();
}
