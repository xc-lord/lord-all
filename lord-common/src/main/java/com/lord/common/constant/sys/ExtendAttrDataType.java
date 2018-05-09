package com.lord.common.constant.sys;

import com.lord.common.constant.BaseEnumType;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：扩展属性的数据类型
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月20日 16:43
 */
public enum ExtendAttrDataType implements BaseEnumType {
    Varchar("字符串"),
    Number("数值"),
    Datetime("时间"),
    Bit("布尔值"),
    ;

    private String name;

    ExtendAttrDataType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static Map<String,String> toMap()
    {
        Map<String, String> map = new HashMap<>();
        ExtendAttrDataType[] values = ExtendAttrDataType.values();
        for (ExtendAttrDataType value : values)
        {
            map.put(value.toString(), value.getName());
        }
        return map;
    }
}
