package com.lord.common.dto;

import java.util.List;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月12日 15:34
 */
public class OptionNode {
    private String value;
    private String label;
    private List<OptionNode> children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<OptionNode> getChildren() {
        return children;
    }

    public void setChildren(List<OptionNode> children) {
        this.children = children;
    }
}
