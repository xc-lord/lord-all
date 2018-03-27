package com.lord.biz.utils;

import com.lord.common.constant.excel.ExcelColumnType;
import com.lord.common.dto.excel.ExcelCellDto;
import com.lord.common.dto.excel.ExcelSheetDto;
import com.lord.utils.exception.CommonException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 功能：Excel工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月27日 10:02
 */
public class ExcelUtils
{
    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 读取Excel文件
     * @param path          文件路径
     * @param sheetIndex    sheet索引
     * @param rowIndex      数据行索引
     * @return
     */
    public static ExcelSheetDto readXls(String path, Integer sheetIndex, Integer rowIndex)
    {
        List<ExcelSheetDto> result = ExcelUtils.readXls(path, rowIndex);
        for (ExcelSheetDto sheet : result)
        {
            if(sheet.getIndex() == sheetIndex)
                return sheet;
        }
        return null;
    }

    /**
     * 读取Excel文件
     * @param path          文件路径
     * @return
     */
    public static List<ExcelSheetDto> readXls(String path)
    {
        return readXls(path, 1);
    }

    /**
     * 读取Excel文件
     * @param path          文件路径
     * @param rowIndex      数据行索引
     * @return
     */
    public static List<ExcelSheetDto> readXls(String path, Integer rowIndex)
    {
        List<ExcelSheetDto> result = new ArrayList<>();
        InputStream is = null;
        try
        {
            is = new FileInputStream(path);
            // HSSFWorkbook 标识整个excel
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            int size = xssfWorkbook.getNumberOfSheets();
            // 循环每一页，并处理当前循环页
            for (int numSheet = 0; numSheet < size; numSheet++)
            {
                // HSSFSheet 标识某一页
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
                if (xssfSheet == null) continue;
                ExcelSheetDto sheetDto = new ExcelSheetDto();
                sheetDto.setSheetName(xssfSheet.getSheetName());
                sheetDto.setIndex(numSheet);
                List<List<ExcelCellDto>> rowList = getRowList(xssfSheet, rowIndex);

                sheetDto.setRows(rowList);
                result.add(sheetDto);
            }
        } catch (Exception e) {
            logger.error("读取Excel文件出现错误：" + e.getMessage(), e);
            throw new CommonException("读取Excel文件出现错误：" + e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                logger.error("关闭Excel文件流出现错误：" + e.getMessage(), e);
            }

        }
        return result;
    }

    private static List<List<ExcelCellDto>> getRowList(XSSFSheet xssfSheet, Integer rowIndex)
    {
        // 处理当前页，循环读取每一行
        List<List<ExcelCellDto>> rowList = new ArrayList<>();
        for (int rowNum = rowIndex; rowNum <= xssfSheet.getLastRowNum(); rowNum++)
        {
            // HSSFRow表示行
            XSSFRow xssfRow = xssfSheet.getRow(rowNum);
            int minColIx = xssfRow.getFirstCellNum();
            int maxColIx = xssfRow.getLastCellNum();
            List<ExcelCellDto> columnList = new ArrayList<>();
            // 遍历改行，获取处理每个cell元素
            for (int colIx = minColIx; colIx < maxColIx; colIx++)
            {
                // HSSFCell 表示单元格
                XSSFCell cell = xssfRow.getCell(colIx);
                if (cell == null) continue;

                ExcelCellDto cellDto = getExcelCellDto(cell);
                cellDto.setRowNum(rowNum);
                cellDto.setColumnNum(colIx);
                columnList.add(cellDto);
            }
            rowList.add(columnList);
        }
        return rowList;
    }

    public static ExcelCellDto getExcelCellDto(XSSFCell cell) {
        ExcelCellDto cellDto = new ExcelCellDto();
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_BOOLEAN:
            cellDto.setColumnType(ExcelColumnType.Bit);
            cellDto.setBooleanValue(cell.getBooleanCellValue());
            return cellDto;
        case Cell.CELL_TYPE_FORMULA:
            cellDto.setColumnType(ExcelColumnType.Varchar);
            cellDto.setStringValue(cell.getCellFormula().toString());
            return cellDto;
        case Cell.CELL_TYPE_NUMERIC:
            if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
                Date date = cell.getDateCellValue();
                cellDto.setColumnType(ExcelColumnType.Datetime);
                cellDto.setDateValue(date);
                return cellDto;
            } else {  //否
                cellDto.setColumnType(ExcelColumnType.Number);
                cellDto.setDoubleValue(cell.getNumericCellValue());
                return cellDto;
            }
        case Cell.CELL_TYPE_STRING:
            cellDto.setColumnType(ExcelColumnType.Varchar);
            cellDto.setStringValue(cell.getStringCellValue());
            return cellDto;
        default:
            cellDto.setColumnType(ExcelColumnType.Varchar);
            return cellDto;
        }
    }
}
