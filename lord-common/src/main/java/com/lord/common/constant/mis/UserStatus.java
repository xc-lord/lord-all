package com.lord.common.constant.mis;

import com.lord.common.constant.BaseEnumType;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：用户状态
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年04月18日 16:43
 */
public enum UserStatus implements BaseEnumType {
    Normal("正常"),
    Frozen("冻结"),
    Invalid("无效"),
    ;

    private String name;

    UserStatus(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public static Map<String,String> toMap()
    {
        Map<String, String> map = new HashMap<>();
        UserStatus[] values = UserStatus.values();
        for (UserStatus value : values)
        {
            map.put(value.toString(), value.getName());
        }
        return map;
    }
}
