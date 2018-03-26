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
public enum ExcelImportWay implements BaseEnumType {
    CoverAll("全量导入"),
    CoverOld("覆盖导入"),
    CoverAppend("追加导入"),
    ;

    private String name;

    ExcelImportWay(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static Map<String,String> toMap()
    {
        Map<String, String> map = new HashMap<>();
        ExcelImportWay[] values = ExcelImportWay.values();
        for (ExcelImportWay value : values)
        {
            map.put(value.toString(), value.getName());
        }
        return map;
    }
}
