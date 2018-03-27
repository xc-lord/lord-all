package com.lord.common.dto.excel;

import com.lord.common.constant.excel.ExcelColumnType;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月27日 16:54
 */
public class ExcelDataColumn
{
    private String name;
    private String field;
    private String type;

    public ExcelDataColumn()
    {
    }

    public ExcelDataColumn(String name, String field, String type)
    {
        this.name = name;
        this.field = field;
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getField()
    {
        return field;
    }

    public void setField(String field)
    {
        this.field = field;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
