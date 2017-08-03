package com.lord.common.dto.ads;

import com.lord.common.constant.ads.AdsAppType;
import com.lord.common.constant.ads.AdsPageState;
import com.lord.common.constant.ads.AdsPageType;

import java.io.Serializable;

/**
 * 功能：页面的查询条件
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 10:06
 */
public class AdsPageQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 页面名称
     */
    private String name;

    /**
     * 页面代码
     */
    private String pageCode;

    /**
     * 平台类型(PC、WX、APP)
     */
    private AdsAppType appType;

    /**
     * 页面类型：常规、专题
     */
    private AdsPageType pageType;

    /**
     * 页面状态
     */
    private AdsPageState pageState;

    /** 每页的大小 */
    private Integer pageSize = 10;

    /** 当前页数 */
    private Integer page = 1;

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

    public String getPageCode()
    {
        return pageCode;
    }

    public void setPageCode(String pageCode)
    {
        this.pageCode = pageCode;
    }

    public AdsAppType getAppType()
    {
        return appType;
    }

    public void setAppType(AdsAppType appType)
    {
        this.appType = appType;
    }

    public AdsPageType getPageType()
    {
        return pageType;
    }

    public void setPageType(AdsPageType pageType)
    {
        this.pageType = pageType;
    }

    public AdsPageState getPageState()
    {
        return pageState;
    }

    public void setPageState(AdsPageState pageState)
    {
        this.pageState = pageState;
    }

    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public Integer getPage()
    {
        return page;
    }

    public void setPage(Integer page)
    {
        this.page = page;
    }
}
