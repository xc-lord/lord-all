package com.lord.biz.utils;

import com.lord.common.dto.excel.ExcelSheetDto;
import org.junit.Test;

import java.util.List;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月26日 20:55
 */
public class TestExcelUtils
{
    @Test
    public void testReadExcel() {
        String path = "D:/data/upload/document/test01.xlsx";
        try {
            List<ExcelSheetDto> result = ExcelUtils.readXls(path);
            System.out.println(result.size());
            for (ExcelSheetDto sheet : result)
            {
                System.out.print(sheet.getSheetName() + "\n");
                System.out.println(sheet.toRowString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
