package com.lord.common.dto;

/**
 * 功能：通用的分类属性接口
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月10日 15:18
 */
public interface Category {
    /** 主键Id */
    Long getId();

    /** 主键Id */
    void setId(Long id);

    /** 名称 */
    String getName();

    /** 名称 */
    void setName(String name);

    /** 等级 */
    Integer getLevel();

    /** 等级 */
    void setLevel(Integer level);

    /** 拼音 */
    String getLetter();

    /** 拼音 */
    void setLetter(String letter);

    /** 图标 */
    String getIcon();

    /** 图标 */
    void setIcon(String icon);

    /** 父节点Id */
    Long getParentId();

    /** 父节点Id */
    void setParentId(Long parentId);

    /** 父节点名称 */
    String getParentName();

    /** 父节点名称 */
    void setParentName(String parentName);

    /** 所有父节点Id */
    String getParentIds();

    /** 所有父节点Id */
    void setParentIds(String parentIds);

    /** 是否叶子节点 */
    Boolean isLeaf();

    /** 是否叶子节点 */
    void setLeaf(Boolean leaf);

    /** 所有子节点Id */
    String getChildrenIds();

    /** 所有子节点Id */
    void setChildrenIds(String childrenIds);

    /** 排序值 */
    Long getOrderValue();

    /** 排序值 */
    void setOrderValue(Long orderValue);
}
