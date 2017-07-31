package com.lord.common.dto.cat;

import java.util.List;

/**
 * 功能：树形节点
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月12日 15:34
 */
public class TreeNode {
    private Long id;
    private String name;
    private String letter;
    private String icon;
    private List<TreeNode> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
