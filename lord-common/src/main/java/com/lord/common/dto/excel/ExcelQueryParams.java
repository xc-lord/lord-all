package com.lord.common.dto.excel;

import com.lord.common.dto.QueryParams;

/**
 * 功能：Excel模板查询对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月16日 16:45
 */
public class ExcelQueryParams extends QueryParams
{
    private Long templateId;//Excel模板ID
    private Long categoryId;//Excel分类ID
    private String excelName;//Excel名称
    private String tableName;//数据库表名

    public Long getTemplateId()
    {
        return templateId;
    }

    public void setTemplateId(Long templateId)
    {
        this.templateId = templateId;
    }

    public Long getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getExcelName()
    {
        return excelName;
    }

    public void setExcelName(String excelName)
    {
        this.excelName = excelName;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }
}
