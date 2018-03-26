package com.lord.web.controller.excel;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.dto.excel.ExcelQueryParams;
import com.lord.common.model.excel.ExcelImportRecord;
import com.lord.common.service.excel.ExcelImportRecordService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import com.lord.web.handler.UserHandler;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：Excel导入记录excel_import_record的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月26日 11:31:45
 */
@RestController
@Api(description = "Excel导入记录API")
public class ExcelImportRecordController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ExcelImportRecordService excelImportRecordService;

    @ApiOperation(value = "查询Excel导入记录的列表")
    @RequestMapping(value = "/api/admin/excel/excelImportRecord/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute ExcelQueryParams queryParams) {
        Pager<ExcelImportRecord> pager = excelImportRecordService.pageExcelImportRecord(queryParams, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新Excel导入记录")
    @RequestMapping(value = "/api/admin/excel/excelImportRecord/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute ExcelImportRecord pageObj) {
        ExcelImportRecord dbObj = excelImportRecordService.saveOrUpdate(pageObj, UserHandler.getLoginUser());
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除Excel导入记录", notes="根据主键id，删除Excel导入记录")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/excel/excelImportRecord/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //excelImportRecordService.removeExcelImportRecord(ids);//逻辑删除
        excelImportRecordService.deleteExcelImportRecord(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="导入Excel数据", notes="根据主键id，导入Excel数据")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/excel/excelImportRecord/importData", method = RequestMethod.GET)
    public Result importData(Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        excelImportRecordService.importData(id);//导入Excel数据
        return Result.success("导入Excel数据成功");
    }

    @ApiOperation(value="删除已经导入数据", notes="根据主键id，删除已经导入数据")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/excel/excelImportRecord/deleteData", method = RequestMethod.GET)
    public Result deleteData(Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        excelImportRecordService.deleteData(id);//删除已经导入数据
        return Result.success("删除已经导入数据成功");
    }

    @ApiOperation(value="获取Excel导入记录", notes="根据主键id，获取Excel导入记录")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/excel/excelImportRecord/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        ExcelImportRecord dbObj = excelImportRecordService.getExcelImportRecord(id);
        return Result.success("获取成功", dbObj);
    }


    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/excel/excelImportRecord/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = excelImportRecordService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
