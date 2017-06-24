package com.lord.common;

import com.lord.common.constant.BaseEnumType;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年04月18日 16:52
 */
public class TestEnum {

    @Test
    public void testEnum() {
        try {
            Class cls = Class.forName("com.lord.common.constant.mis.MisUserStatus");
            Method method = cls.getMethod("values");
            BaseEnumType inter[] = (BaseEnumType[]) method.invoke(null, null);
            for (BaseEnumType enumMessage : inter) {
                System.out.println(enumMessage.getName() + ":" + enumMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
