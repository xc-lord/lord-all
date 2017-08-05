package com.lord.common.dto.ads;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：页面的模板
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月05日 10:58
 */
public class AdsPageTemplate implements Serializable
{
    private static final long serialVersionUID = 1L;
    /** 页面Id */
    private Long id;
    /** 页面名称 */
    private String page_name;
    /** 页面编码 */
    private String page_code;
    /** 广告位模板 */
    private List<AdsTemplate> templates;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getPage_name()
    {
        return page_name;
    }

    public void setPage_name(String page_name)
    {
        this.page_name = page_name;
    }

    public String getPage_code()
    {
        return page_code;
    }

    public void setPage_code(String page_code)
    {
        this.page_code = page_code;
    }

    public List<AdsTemplate> getTemplates()
    {
        return templates;
    }

    public void setTemplates(List<AdsTemplate> templates)
    {
        this.templates = templates;
    }
}
