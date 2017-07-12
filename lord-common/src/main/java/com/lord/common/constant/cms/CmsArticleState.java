package com.lord.common.constant.cms;

import com.lord.common.constant.BaseEnumType;

/**
 * 功能：文章的状态
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年04月18日 16:43
 */
public enum CmsArticleState implements BaseEnumType {
    Create("新建"),
    WaiteModify("待修改"),
    WaiteCheck("待审核"),
    WaiteRelease("待发布"),
    Release("发布中"),
    ;

    private String name;

    CmsArticleState(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
