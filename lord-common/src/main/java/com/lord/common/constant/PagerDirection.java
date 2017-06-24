package com.lord.common.constant;

/**
 * 功能：排序使用的枚举
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年04月02日 14:56
 */
public enum PagerDirection {
    ASC("正序"), DESC("倒序");

    private String name;

    PagerDirection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
