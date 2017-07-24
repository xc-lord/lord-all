package com.lord.utils;

import com.lord.utils.dto.Code;
import com.lord.utils.exception.CommonException;
import org.apache.commons.lang3.StringUtils;

/**
 * 功能：参数检查的工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年03月2017/3/30日
 */
public class Preconditions {

    /**
     * 检查condition是否为真，为真抛出异常
     * @param condition 条件
     */
    public static void checkArgument(boolean condition) {
        checkArgument(condition,"参数错误");
    }

    /**
     * 检查condition是否为真，为真抛出异常
     * @param condition 条件
     * @param msg       错误消息
     */
    public static void checkArgument(boolean condition, String msg)
    {
        checkArgument(condition, msg, Code.ReqFailure);
    }

    /**
     * 检查condition是否为真，为真抛出异常
     * @param condition 条件
     * @param msg       错误消息
     * @param code      错误类型
     */
    public static void checkArgument(boolean condition, String msg, Code code) {
        if (condition) {
            throw new CommonException(code, msg);
        }
    }

    /**
     * 检查obj是否为空，为空抛出异常。
     * @param obj   对象
     */
    public static void checkNotNull(Object obj) {
        checkNotNull(obj, "参数不能为空");
    }

    /**
     * 检查condition是否为空，为空抛出异常。
     * @param obj   对象
     * @param msg   错误消息
     */
    public static void checkNotNull(Object obj, String msg)
    {
        checkNotNull(obj, msg, Code.ReqFailure);
    }

    /**
     * 检查condition是否为空，为空抛出异常。
     * @param obj   对象
     * @param msg   错误消息
     * @param code  错误类型
     */
    public static void checkNotNull(Object obj, String msg, Code code) {
        if (obj == null) {
            throw new CommonException(code, msg);
        }
        if(obj instanceof String) {
            String str = (String) obj;
            if (StringUtils.isEmpty(str)) {
                throw new CommonException(code, msg);
            }
        }
    }
}
