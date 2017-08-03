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
public enum AdsPageType implements BaseEnumType {
    Normal("普通"),
    Promotion("促销"),
    ;

    private String name;

    AdsPageType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static Map<String,String> toMap()
    {
        Map<String, String> map = new HashMap<>();
        AdsPageType[] values = AdsPageType.values();
        for (AdsPageType value : values)
        {
            map.put(value.toString(), value.getName());
        }
        return map;
    }
}
