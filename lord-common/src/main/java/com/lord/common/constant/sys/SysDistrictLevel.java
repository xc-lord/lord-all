package com.lord.common.constant.sys;

import com.lord.common.constant.BaseEnumType;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年04月26日 20:36
 */
public enum SysDistrictLevel implements BaseEnumType
{
    country("国家"),
    province("省份"),
    city("市"),
    district("区县"),
    street("街道"),
    ;

    private String name;

    SysDistrictLevel(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static Map<String,String> toMap()
    {
        Map<String, String> map = new HashMap<>();
        SysDistrictLevel[] values = SysDistrictLevel.values();
        for (SysDistrictLevel value : values)
        {
            map.put(value.toString(), value.getName());
        }
        return map;
    }
}
