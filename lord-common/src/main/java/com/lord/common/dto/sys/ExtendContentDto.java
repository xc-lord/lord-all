package com.lord.common.dto.sys;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月29日 20:23
 */
public class ExtendContentDto
{
    private Long id;

    /**
     * 实体ID
     */
    private Long entityId;

    /**
     * 实体编码
     */
    private String entityCode;

    /**
     * 内容
     */
    private String content;

    /**
     * 编辑的内容
     */
    private String contentEdit;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getEntityId()
    {
        return entityId;
    }

    public void setEntityId(Long entityId)
    {
        this.entityId = entityId;
    }

    public String getEntityCode()
    {
        return entityCode;
    }

    public void setEntityCode(String entityCode)
    {
        this.entityCode = entityCode;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContentEdit()
    {
        return contentEdit;
    }

    public void setContentEdit(String contentEdit)
    {
        this.contentEdit = contentEdit;
    }
}
