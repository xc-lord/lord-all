package com.lord.common.dto.excel;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能：Excel列关系映射
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月20日 17:11
 */
public class ExcelColumnDto implements Serializable
{
    private Long id;
    private Long excelTemplateId;
    private String excelColumn;
    private String dbColumn;
    private String columnType;
    private Integer columnLength;
    private Boolean nullable;
    private Boolean identification;
    private Date startTime;
    private Date endTime;
    private Double minValue;
    private Double maxValue;
    private Long orderValue;
    private String timeFormat;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getExcelTemplateId()
    {
        return excelTemplateId;
    }

    public void setExcelTemplateId(Long excelTemplateId)
    {
        this.excelTemplateId = excelTemplateId;
    }

    public String getExcelColumn()
    {
        return excelColumn;
    }

    public void setExcelColumn(String excelColumn)
    {
        this.excelColumn = excelColumn;
    }

    public String getDbColumn()
    {
        return dbColumn;
    }

    public void setDbColumn(String dbColumn)
    {
        this.dbColumn = dbColumn;
    }

    public String getColumnType()
    {
        return columnType;
    }

    public void setColumnType(String columnType)
    {
        this.columnType = columnType;
    }

    public Integer getColumnLength()
    {
        return columnLength;
    }

    public void setColumnLength(Integer columnLength)
    {
        this.columnLength = columnLength;
    }

    public Boolean getNullable()
    {
        return nullable;
    }

    public void setNullable(Boolean nullable)
    {
        this.nullable = nullable;
    }

    public Boolean getIdentification()
    {
        return identification;
    }

    public void setIdentification(Boolean identification)
    {
        this.identification = identification;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Double getMinValue()
    {
        return minValue;
    }

    public void setMinValue(Double minValue)
    {
        this.minValue = minValue;
    }

    public Double getMaxValue()
    {
        return maxValue;
    }

    public void setMaxValue(Double maxValue)
    {
        this.maxValue = maxValue;
    }

    public Long getOrderValue()
    {
        return orderValue;
    }

    public void setOrderValue(Long orderValue)
    {
        this.orderValue = orderValue;
    }

    public String getTimeFormat()
    {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat)
    {
        this.timeFormat = timeFormat;
    }

    @Override
    public String toString()
    {
        return "ExcelColumnDto{" +
                "excelColumn='" + excelColumn + '\'' +
                ", excelTemplateId=" + excelTemplateId +
                ", id=" + id +
                ", dbColumn='" + dbColumn + '\'' +
                ", columnType='" + columnType + '\'' +
                '}';
    }
}
