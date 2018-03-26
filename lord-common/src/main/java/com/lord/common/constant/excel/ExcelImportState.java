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
public enum ExcelImportState implements BaseEnumType {
    NotImported("未导入"),
    Imported("已导入"),
    ReImported("已重新导入"),
    RemoveImported("已删除导入数据")
    ;

    private String name;

    ExcelImportState(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static Map<String,String> toMap()
    {
        Map<String, String> map = new HashMap<>();
        ExcelImportState[] values = ExcelImportState.values();
        for (ExcelImportState value : values)
        {
            map.put(value.toString(), value.getName());
        }
        return map;
    }
}
