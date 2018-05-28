package com.lord.common.dto.sys;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月24日 11:48
 */
public class ExtendDetails implements Serializable
{
    /**
     * 模板ID
     */
    private Long templateId;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 实体ID
     */
    private Long entityId;

    /**
     * 实体表名
     */
    private String entityTable;

    /**
     * 实体编码
     */
    private String entityCode;

    /**
     * 列信息
     */
    private List<ExtendAttrDto> columnList;

    public Long getTemplateId()
    {
        return templateId;
    }

    public void setTemplateId(Long templateId)
    {
        this.templateId = templateId;
    }

    public String getTemplateName()
    {
        return templateName;
    }

    public void setTemplateName(String templateName)
    {
        this.templateName = templateName;
    }

    public Long getEntityId()
    {
        return entityId;
    }

    public void setEntityId(Long entityId)
    {
        this.entityId = entityId;
    }

    public String getEntityTable()
    {
        return entityTable;
    }

    public void setEntityTable(String entityTable)
    {
        this.entityTable = entityTable;
    }

    public String getEntityCode()
    {
        return entityCode;
    }

    public void setEntityCode(String entityCode)
    {
        this.entityCode = entityCode;
    }

    public List<ExtendAttrDto> getColumnList()
    {
        return columnList;
    }

    public void setColumnList(List<ExtendAttrDto> columnList)
    {
        this.columnList = columnList;
    }
}
