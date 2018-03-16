package com.lord.web.controller.excel;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.dto.excel.ExcelQueryParams;
import com.lord.common.model.excel.ExcelTemplate;
import com.lord.common.service.excel.ExcelTemplateService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：Excel模板配置excel_template的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月16日 15:51:05
 */
@RestController
@Api(description = "Excel模板配置API")
public class ExcelTemplateController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ExcelTemplateService excelTemplateService;

    @ApiOperation(value = "查询Excel模板配置的列表")
    @RequestMapping(value = "/api/admin/excel/excelTemplate/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute ExcelQueryParams queryParams) {
        Pager<ExcelTemplate> pager = excelTemplateService.pageExcelTemplate(queryParams, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新Excel模板配置")
    @RequestMapping(value = "/api/admin/excel/excelTemplate/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute ExcelTemplate pageObj) {
        ExcelTemplate dbObj = excelTemplateService.saveOrUpdate(pageObj);
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除Excel模板配置", notes="根据主键id，删除Excel模板配置")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/excel/excelTemplate/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //excelTemplateService.removeExcelTemplate(ids);//逻辑删除
        excelTemplateService.deleteExcelTemplate(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取Excel模板配置", notes="根据主键id，获取Excel模板配置")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/excel/excelTemplate/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        ExcelTemplate dbObj = excelTemplateService.getExcelTemplate(id);
        return Result.success("获取成功", dbObj);
    }

    @ApiOperation(value="更新Excel模板配置的排序值", notes="根据主键id，更新Excel模板配置的排序值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderValue", value = "排序值", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/api/admin/excel/excelTemplate/updateOrderValue", method = RequestMethod.GET)
    public Result updateOrderValue(Long id, Long orderValue)
    {
        Preconditions.checkNotNull(id, "id不能为空");
        Preconditions.checkNotNull(orderValue, "排序值orderValue不能为空");
        excelTemplateService.updateOrderValue(id, orderValue);
        return Result.success("更新成功");
    }

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/excel/excelTemplate/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = excelTemplateService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
