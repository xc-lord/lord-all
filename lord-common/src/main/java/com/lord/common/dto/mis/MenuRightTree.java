package com.lord.common.dto.mis;

import java.util.List;

/**
 * 功能：菜单的权限管理树形结构
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月31日 10:38
 */
public class MenuRightTree
{
    /**
     * 已经选择的菜单Id
     */
    private List<Long> selectMenuIds;
    /**
     * 树形结构的节点数据
     */
    private List<MenuRightNode> treeNodes;

    public List<Long> getSelectMenuIds()
    {
        return selectMenuIds;
    }

    public void setSelectMenuIds(List<Long> selectMenuIds)
    {
        this.selectMenuIds = selectMenuIds;
    }

    public List<MenuRightNode> getTreeNodes()
    {
        return treeNodes;
    }

    public void setTreeNodes(List<MenuRightNode> treeNodes)
    {
        this.treeNodes = treeNodes;
    }
}
