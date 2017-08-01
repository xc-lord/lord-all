package com.lord.common.dto.mis;

import com.lord.common.model.mis.MisMenuRight;

import java.util.List;

/**
 * 功能：菜单的权限管理树形结构的节点
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月31日 10:38
 */
public class MenuRightNode
{
    private Long id;
    private String name;
    private List<MisMenuRight> rights;
    private List<MenuRightNode> children;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<MisMenuRight> getRights()
    {
        return rights;
    }

    public void setRights(List<MisMenuRight> rights)
    {
        this.rights = rights;
    }

    public List<MenuRightNode> getChildren()
    {
        return children;
    }

    public void setChildren(List<MenuRightNode> children)
    {
        this.children = children;
    }
}
