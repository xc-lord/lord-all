package com.lord.common.dto.excel;

import com.lord.common.constant.excel.ExcelColumnType;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能：Excel单元格类型
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月27日 08:49
 */
public class ExcelCellDto implements Serializable
{
    /**
     * Excel列名
     */
    private String excelColumn;

    /**
     * 数据库列名
     */
    private String dbColumn;

    /**
     * 数据类型
     */
    private ExcelColumnType columnType;

    /**
     * 字符串值
     */
    private String stringValue;

    /**
     * 布尔值
     */
    private Boolean booleanValue;

    /**
     * 数值
     */
    private Double doubleValue;

    /**
     * 时间值
     */
    private Date dateValue;

    /**
     * 第几行
     */
    private int rowNum;

    /**
     * 第几列
     */
    private int columnNum;

    /**
     * 是否唯一标识
     */
    private boolean identification;

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

    public ExcelColumnType getColumnType()
    {
        return columnType;
    }

    public void setColumnType(ExcelColumnType columnType)
    {
        this.columnType = columnType;
    }

    public String getStringValue()
    {
        return stringValue;
    }

    public void setStringValue(String stringValue)
    {
        this.stringValue = stringValue;
    }

    public Double getDoubleValue()
    {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue)
    {
        this.doubleValue = doubleValue;
    }

    public Date getDateValue()
    {
        return dateValue;
    }

    public void setDateValue(Date dateValue)
    {
        this.dateValue = dateValue;
    }

    public Boolean getBooleanValue()
    {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue)
    {
        this.booleanValue = booleanValue;
    }

    public int getRowNum()
    {
        return rowNum;
    }

    public void setRowNum(int rowNum)
    {
        this.rowNum = rowNum;
    }

    public int getColumnNum()
    {
        return columnNum;
    }

    public void setColumnNum(int columnNum)
    {
        this.columnNum = columnNum;
    }

    public boolean isIdentification()
    {
        return identification;
    }

    public void setIdentification(boolean identification)
    {
        this.identification = identification;
    }

    @Override
    public String toString()
    {
        String value = this.getStringValue();
        if (ExcelColumnType.Varchar.equals(this.getColumnType()))
        {
            value = this.getStringValue();
        } else if (ExcelColumnType.Number.equals(this.getColumnType()))
        {
            value = this.getDoubleValue() + "";
        } else if (ExcelColumnType.Datetime.equals(this.getColumnType()) && this.getDateValue() != null)
        {
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
            value = sdformat.format(this.getDateValue());
        } else if (ExcelColumnType.Bit.equals(this.getColumnType()) && this.getBooleanValue() != null)
        {
            value = this.getBooleanValue() + "";
        }
        return this.getDbColumn() + "=" + value;
    }

    public Object getValue()
    {
        if (ExcelColumnType.Varchar.equals(getColumnType()))
        {
            return getStringValue();
        } else if (ExcelColumnType.Number.equals(this.getColumnType()))
        {
            return getDoubleValue();
        } else if (ExcelColumnType.Datetime.equals(this.getColumnType()) && this.getDateValue() != null)
        {
            return getDateValue();
        } else if (ExcelColumnType.Bit.equals(this.getColumnType()) && this.getBooleanValue() != null)
        {
            return getBooleanValue();
        }
        return null;
    }
}
