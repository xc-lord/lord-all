package com.lord.common.dto.cat;

/**
 * 功能：简单的分类属性接口
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月10日 15:18
 */
public interface CategorySimple
{
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

    /** 父节点Id */
    Long getParentId();

    /** 父节点Id */
    void setParentId(Long parentId);

    /** 排序值 */
    Long getOrderValue();

    /** 排序值 */
    void setOrderValue(Long orderValue);

    String getAdsType();

    void setAdsType(String adsType);
}
