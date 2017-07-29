package com.lord.common.constant.mis;

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
public enum MenuBaseRight implements BaseEnumType {
    Add("新增"),
    Delete("删除"),
    Update("编辑"),
    View("查看"),
    Search("搜索")
    ;

    private String name;

    MenuBaseRight(String name) {
        this.name = name;
    }

    public static Map<String,String> toMap()
    {
        Map<String, String> map = new HashMap<>();
        MenuBaseRight[] values = MenuBaseRight.values();
        for (MenuBaseRight value : values)
        {
            map.put(value.toString(), value.getName());
        }
        return map;
    }

    @Override
    public String getName() {
        return name;
    }
}
