package com.lord.common.dto.excel;

import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月27日 16:52
 */
public class ExcelDataDto
{
    private List<Map<String,Object>> dataList;
    private List<ExcelDataColumn> columnList;

    public List<Map<String, Object>> getDataList()
    {
        return dataList;
    }

    public void setDataList(List<Map<String, Object>> dataList)
    {
        this.dataList = dataList;
    }

    public List<ExcelDataColumn> getColumnList()
    {
        return columnList;
    }

    public void setColumnList(List<ExcelDataColumn> columnList)
    {
        this.columnList = columnList;
    }
}
