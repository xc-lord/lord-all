package com.lord.common.constant.mis;

import com.lord.common.constant.BaseEnumType;

/**
 * 功能：用户状态
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年04月18日 16:43
 */
public enum MisUserStatus implements BaseEnumType {
    Normal("正常"),
    Frozen("冻结"),
    Invalid("无效"),
    ;

    private String name;

    MisUserStatus(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
