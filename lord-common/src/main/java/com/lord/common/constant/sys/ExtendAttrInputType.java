package com.lord.common.constant.sys;

import com.lord.common.constant.BaseEnumType;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：扩展属性的表单类型
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月20日 16:43
 */
public enum ExtendAttrInputType implements BaseEnumType {
    Input("输入框"),
    Radio("单选框"),
    Checkbox("多选框"),
    Select("下拉选择"),
    DatePicker("时间选择"),
    Upload("上传"),
    Switch("开关"),
    ;

    private String name;

    ExtendAttrInputType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static Map<String,String> toMap()
    {
        Map<String, String> map = new HashMap<>();
        ExtendAttrInputType[] values = ExtendAttrInputType.values();
        for (ExtendAttrInputType value : values)
        {
            map.put(value.toString(), value.getName());
        }
        return map;
    }
}
