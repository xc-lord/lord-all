package com.lord.common.dto.ads;

import com.lord.common.model.ads.AdsPage;

import java.io.Serializable;
import java.util.Map;

/**
 * 功能：页面信息
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月07日 09:49
 */
public class AdsPageInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * 页面的信息
     */
    private AdsPage page;

    /**
     * 页面的广告位具体信息
     */
    private Map<String, AdsSpaceInfo> spaceMap;

    /**
     * 页面的广告位树形Map
     */
    private Map<String, AdsTemplate> treeMap;

    /**
     * 加载时间
     */
    private Long loadTime;

    public AdsPage getPage()
    {
        return page;
    }

    public void setPage(AdsPage page)
    {
        this.page = page;
    }

    public Map<String, AdsSpaceInfo> getSpaceMap()
    {
        return spaceMap;
    }

    public void setSpaceMap(Map<String, AdsSpaceInfo> spaceMap)
    {
        this.spaceMap = spaceMap;
    }

    public Map<String, AdsTemplate> getTreeMap()
    {
        return treeMap;
    }

    public void setTreeMap(Map<String, AdsTemplate> treeMap)
    {
        this.treeMap = treeMap;
    }

    public Long getLoadTime()
    {
        return loadTime;
    }

    public void setLoadTime(Long loadTime)
    {
        this.loadTime = loadTime;
    }
}
