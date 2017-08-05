package com.lord.common.dto.ads;

import com.lord.common.constant.ads.AdsSpaceType;

import java.io.Serializable;
import java.util.List;

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
    /** 模板Id */
    private Long id;
    /** 模板名称 */
    private String name;
    /** 模板关键字 */
    private String keyword;
    /** 模板类型 */
    private AdsSpaceType type;
    /** 元素个数 */
    private Integer count;
    /** 模板个数 */
    private Integer template_num;
    /** 配置说明 */
    private String intro;
    /** 查询模板的子模板时是否需要根据配置进行筛选 */
    private Boolean choose = false;
    /** 筛选子模板时，该模板是否默认的选项 */
    private Boolean isDefault = true;
    /** 子模板 */
    private List<AdsTemplate> children;
    /** 同级的重复模板 */
    private List<AdsTemplate> subList;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public List<AdsTemplate> getSubList()
    {
        return subList;
    }

    public void setSubList(List<AdsTemplate> subList)
    {
        this.subList = subList;
    }

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

    public AdsSpaceType getType()
    {
        return type;
    }

    public void setType(AdsSpaceType type)
    {
        this.type = type;
    }

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }

    public Integer getTemplate_num()
    {
        return template_num;
    }

    public void setTemplate_num(Integer template_num)
    {
        this.template_num = template_num;
    }

    public String getIntro()
    {
        return intro;
    }

    public void setIntro(String intro)
    {
        this.intro = intro;
    }

    public Boolean getChoose()
    {
        return choose;
    }

    public void setChoose(Boolean choose)
    {
        this.choose = choose;
    }

    public Boolean getIsDefault()
    {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault)
    {
        this.isDefault = isDefault;
    }

    public List<AdsTemplate> getChildren()
    {
        return children;
    }

    public void setChildren(List<AdsTemplate> children)
    {
        this.children = children;
    }
}
