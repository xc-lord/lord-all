package com.lord.common.constant.auth;

import com.lord.common.constant.BaseEnumType;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能：菜单的基础权限
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年04月18日 16:43
 */
public enum AuthCode implements BaseEnumType {
    //文章的权限
    CmsArticleAdd("文章新增", "cmsArticle.Add"),
    CmsArticleDelete("文章删除", "cmsArticle.Delete"),
    CmsArticleUpdate("文章编辑", "cmsArticle.Update"),
    CmsArticleView("文章查看", "cmsArticle.View"),
    CmsArticleSearch("文章搜索", "cmsArticle.Search"),
    ;

    private String name;
    private String code;

    AuthCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static Map<String,String> toMap()
    {
        Map<String, String> map = new HashMap<>();
        AuthCode[] values = AuthCode.values();
        for (AuthCode value : values)
        {
            map.put(value.toString(), value.getName());
        }
        return map;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getCode()
    {
        return code;
    }
}
