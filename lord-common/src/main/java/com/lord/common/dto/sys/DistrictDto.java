package com.lord.common.dto.sys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：行政区域Dto
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月05日 20:09
 */
public class DistrictDto implements Serializable
{
    /**
     * 区域code
     */
    private Long id;

    /**
     * 区域名称
     */
    private String name;

    /**
     * 子区域内容
     */
    private List<DistrictDto> children = new ArrayList<>();

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

    public List<DistrictDto> getChildren()
    {
        return children;
    }

    public void setChildren(List<DistrictDto> children)
    {
        this.children = children;
    }
}
