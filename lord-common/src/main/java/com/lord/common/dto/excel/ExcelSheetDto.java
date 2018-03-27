package com.lord.common.dto.excel;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月27日 09:03
 */
public class ExcelSheetDto implements Serializable
{
    private String sheetName;
    private int index;
    private List<List<ExcelCellDto>> rows;

    public String getSheetName()
    {
        return sheetName;
    }

    public void setSheetName(String sheetName)
    {
        this.sheetName = sheetName;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public List<List<ExcelCellDto>> getRows()
    {
        return rows;
    }

    public void setRows(List<List<ExcelCellDto>> rows)
    {
        this.rows = rows;
    }

    public String toRowString()
    {
        StringBuffer sb = new StringBuffer("");
        if (this.getRows() != null)
        {
            for (List<ExcelCellDto> row : rows)
            {
                for (ExcelCellDto cell : row)
                {
                    sb.append(cell).append("\t");
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
