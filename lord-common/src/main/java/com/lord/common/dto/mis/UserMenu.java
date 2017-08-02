package com.lord.common.dto.mis;

import com.lord.common.model.mis.MisMenu;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：用户的菜单
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月02日 16:17
 */
public class UserMenu implements Serializable
{
    /**
     * 有权限的菜单
     */
    List<MisMenu> menus;

    /**
     * 无权限的菜单
     */
    List<MisMenu> notRightMenus;

    public List<MisMenu> getMenus()
    {
        return menus;
    }

    public void setMenus(List<MisMenu> menus)
    {
        this.menus = menus;
    }

    public List<MisMenu> getNotRightMenus()
    {
        return notRightMenus;
    }

    public void setNotRightMenus(List<MisMenu> notRightMenus)
    {
        this.notRightMenus = notRightMenus;
    }
}
