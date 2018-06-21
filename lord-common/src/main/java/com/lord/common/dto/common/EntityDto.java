package com.lord.common.dto.common;

import java.io.Serializable;

/**
 * 功能：实体对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月18日 09:53
 */
public class EntityDto implements Serializable
{
    private Long id;
    private String name;
    private String entityCode;

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

    public String getEntityCode()
    {
        return entityCode;
    }

    public void setEntityCode(String entityCode)
    {
        this.entityCode = entityCode;
    }
}
