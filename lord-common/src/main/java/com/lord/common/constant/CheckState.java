package com.lord.common.constant;

/**
 * 功能：文章的审核状态
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年04月18日 16:43
 */
public enum CheckState implements BaseEnumType {
    WaitCheck("待审核"),
    CheckSuccess("审核通过")
    ;

    private String name;

    CheckState(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
