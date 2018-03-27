package com.lord.utils;

import java.util.Date;

/**
 * 功能：ID生成工具
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年02月03日 15:46
 */
public class IdGenerator
{
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyyMMddHHmmssSSS";
    private static final SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(1L, 1L);

    /**
     * 创建订单号
     * @return 返回订单号
     */
    public static String createOrderId()
    {
        String formatStr = CommonUtils.dateFormat(new Date(), YYYY_MM_DD_HH_MM_SS_SSS) + CommonUtils.genRandom(4);
        if (formatStr.startsWith("20"))
        {
            formatStr = formatStr.substring(2);
        }
        return "D" + formatStr;
    }

    /**
     * 获取自增ID
     * @return 自增ID
     */
    public static long nextId()
    {
        return snowflakeIdWorker.nextId();
    }

    public static void main(String[] args) {
        System.out.println(createOrderId());
        System.out.println(createOrderId());
        System.out.println(createOrderId());
        System.out.println(createOrderId());
        System.out.println(createOrderId());
        System.out.println(createOrderId());
        System.out.println(createOrderId());
        System.out.println(createOrderId());
        System.out.println(createOrderId());
        System.out.println(createOrderId());
    }
}
