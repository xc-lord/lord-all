package com.lord.common.dto.excel;

import com.lord.common.dto.user.LoginUser;

import java.io.Serializable;

/**
 * 功能：Excel模板表单对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月19日 17:01
 */
public class ExcelTemplateFormDto implements Serializable
{
    /**
     * 主键ID
     */
    private Long id;

    /**
     * Excel名称
     */
    private String excelName;

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 是否已经生成表
     */
    private Boolean tableCreated = false;

    /**
     * Excel的sheet索引
     */
    private Integer excelSheetIndex = 1;

    /**
     * Excel起始行
     */
    private Integer excelStartRow = 2;

    /**
     * 标识字段组
     */
    private String identifyColumn;

    /**
     * Excel样例
     */
    private String excelExample;

    /**
     * 分类Id
     */
    private Long categoryId;

    /**
     * 支持全量导入
     */
    private Boolean coverAll = false;

    /**
     * 支持覆盖导入
     */
    private Boolean coverOld = true;

    /**
     * 支持追加导入
     */
    private Boolean coverAppend = true;

    /**
     * 排序
     */
    private Long orderValue;

    /**
     * 登录用户
     */
    private LoginUser loginUser;

    private String columnJsonStr;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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

    public Boolean getTableCreated()
    {
        return tableCreated;
    }

    public void setTableCreated(Boolean tableCreated)
    {
        this.tableCreated = tableCreated;
    }

    public Integer getExcelSheetIndex()
    {
        return excelSheetIndex;
    }

    public void setExcelSheetIndex(Integer excelSheetIndex)
    {
        this.excelSheetIndex = excelSheetIndex;
    }

    public Integer getExcelStartRow()
    {
        return excelStartRow;
    }

    public void setExcelStartRow(Integer excelStartRow)
    {
        this.excelStartRow = excelStartRow;
    }

    public String getIdentifyColumn()
    {
        return identifyColumn;
    }

    public void setIdentifyColumn(String identifyColumn)
    {
        this.identifyColumn = identifyColumn;
    }

    public String getExcelExample()
    {
        return excelExample;
    }

    public void setExcelExample(String excelExample)
    {
        this.excelExample = excelExample;
    }

    public Long getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
    }

    public Boolean getCoverAll()
    {
        return coverAll;
    }

    public void setCoverAll(Boolean coverAll)
    {
        this.coverAll = coverAll;
    }

    public Boolean getCoverOld()
    {
        return coverOld;
    }

    public void setCoverOld(Boolean coverOld)
    {
        this.coverOld = coverOld;
    }

    public Boolean getCoverAppend()
    {
        return coverAppend;
    }

    public void setCoverAppend(Boolean coverAppend)
    {
        this.coverAppend = coverAppend;
    }

    public Long getOrderValue()
    {
        return orderValue;
    }

    public void setOrderValue(Long orderValue)
    {
        this.orderValue = orderValue;
    }

    public LoginUser getLoginUser()
    {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser)
    {
        this.loginUser = loginUser;
    }

    public String getColumnJsonStr()
    {
        return columnJsonStr;
    }

    public void setColumnJsonStr(String columnJsonStr)
    {
        this.columnJsonStr = columnJsonStr;
    }
}
