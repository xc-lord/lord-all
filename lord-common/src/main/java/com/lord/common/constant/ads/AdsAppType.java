package com.lord.common.constant.ads;

import com.lord.common.constant.BaseEnumType;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：页面类型
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年04月18日 16:43
 */
public enum AdsAppType implements BaseEnumType {
    PC("PC端"),
    WAP("H5端"),
    APP("App端"),
    ;

    private String name;

    AdsAppType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static Map<String,String> toMap()
    {
        Map<String, String> map = new HashMap<>();
        AdsAppType[] values = AdsAppType.values();
        for (AdsAppType value : values)
        {
            map.put(value.toString(), value.getName());
        }
        return map;
    }
}
