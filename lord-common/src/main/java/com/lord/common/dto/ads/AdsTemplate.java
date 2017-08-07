package com.lord.common.dto.ads;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 功能：广告位的模板
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月05日 10:58
 */
public class AdsTemplate implements Serializable
{
    private static final long serialVersionUID = 1L;
    /** 模板名称 */
    private String name;
    /** 模板关键字 */
    private String keyword;
    /** 子模板 */
    private Map<String, AdsTemplate> children;
    /** 同级的重复模板 */
    private List<AdsTemplate> subList;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public Map<String, AdsTemplate> getChildren()
    {
        return children;
    }

    public void setChildren(Map<String, AdsTemplate> children)
    {
        this.children = children;
    }

    public List<AdsTemplate> getSubList()
    {
        return subList;
    }

    public void setSubList(List<AdsTemplate> subList)
    {
        this.subList = subList;
    }
}
