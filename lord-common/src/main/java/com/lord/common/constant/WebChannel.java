package com.lord.common.constant;

/**
 * 功能：网站的渠道或来源
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年04月18日 16:43
 */
public enum WebChannel implements BaseEnumType {
    MIS("网站后台"),
    PC("PC端"),
    WAP("H5端"),
    APP("App端"),
    ;

    private String name;

    WebChannel(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
