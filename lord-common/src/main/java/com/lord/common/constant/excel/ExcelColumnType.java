package com.lord.common.constant.excel;

import com.lord.common.constant.BaseEnumType;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：Excel列的类型
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月20日 16:43
 */
public enum ExcelColumnType implements BaseEnumType {
    Varchar("字符串"),
    Number("数字"),
    Datetime("时间"),
    ;

    private String name;

    ExcelColumnType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static Map<String,String> toMap()
    {
        Map<String, String> map = new HashMap<>();
        ExcelColumnType[] values = ExcelColumnType.values();
        for (ExcelColumnType value : values)
        {
            map.put(value.toString(), value.getName());
        }
        return map;
    }
}
