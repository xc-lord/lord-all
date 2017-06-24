package com.lord.utils;

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
     * @param condition
     */
    public static void checkArgument(boolean condition) {
        checkArgument(condition,"参数错误");
    }

    /**
     * 检查condition是否为真，为真抛出异常
     * @param condition
     * @param msg
     */
    public static void checkArgument(boolean condition, String msg) {
        if (condition) {
            throw new CommonException(msg);
        }
    }

    /**
     * 检查condition是否为空，为空抛出异常。
     * @param condition
     */
    public static void checkNotNull(Object condition) {
        checkNotNull(condition, "参数不能为空");
    }

    /**
     * 检查condition是否为空，为空抛出异常。
     * @param condition
     * @param msg
     */
    public static void checkNotNull(Object condition, String msg) {
        if (condition == null) {
            throw new CommonException(msg);
        }
        if(condition instanceof String) {
            String str = (String) condition;
            if (StringUtils.isEmpty(str)) {
                throw new CommonException(msg);
            }
        }
    }
}
