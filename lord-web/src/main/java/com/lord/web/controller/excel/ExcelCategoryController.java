package com.lord.web.controller.excel;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.model.excel.ExcelCategory;
import com.lord.common.service.excel.ExcelCategoryService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：Excel分类excel_category的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月16日 15:37:37
 */
@RestController
@Api(description = "Excel分类API")
public class ExcelCategoryController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ExcelCategoryService excelCategoryService;

    @ApiOperation(value = "查询Excel分类的列表")
    @RequestMapping(value = "/api/admin/excel/excelCategory/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        ExcelCategory param = new ExcelCategory();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
            param.setName(queryParams.getName());
        }
        Pager<ExcelCategory> pager = excelCategoryService.pageExcelCategory(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新Excel分类")
    @RequestMapping(value = "/api/admin/excel/excelCategory/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute ExcelCategory pageObj) {
        ExcelCategory dbObj = excelCategoryService.saveOrUpdate(pageObj);
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除Excel分类", notes="根据主键id，删除Excel分类")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/excel/excelCategory/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //excelCategoryService.removeExcelCategory(ids);//逻辑删除
        excelCategoryService.deleteExcelCategory(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取Excel分类", notes="根据主键id，获取Excel分类")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/excel/excelCategory/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        ExcelCategory dbObj = excelCategoryService.getExcelCategory(id);
        return Result.success("获取成功", dbObj);
    }

    @ApiOperation(value="更新Excel分类的排序值", notes="根据主键id，更新Excel分类的排序值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderValue", value = "排序值", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/api/admin/excel/excelCategory/updateOrderValue", method = RequestMethod.GET)
    public Result updateOrderValue(Long id, Long orderValue)
    {
        Preconditions.checkNotNull(id, "id不能为空");
        Preconditions.checkNotNull(orderValue, "排序值orderValue不能为空");
        excelCategoryService.updateOrderValue(id, orderValue);
        return Result.success("更新成功");
    }

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/excel/excelCategory/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = excelCategoryService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
