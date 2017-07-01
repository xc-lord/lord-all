<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign hasColumn="com.lord.generator.ftl.FtlHasColumnMethod"?new()>
package ${basepackage}.web.controller.${bizPackage};

import ${basepackage}.common.dto.Pager;
import ${basepackage}.common.dto.QueryParams;
import ${basepackage}.common.model.${bizPackage}.${className};
import ${basepackage}.common.service.${bizPackage}.${className}Service;
import ${basepackage}.utils.Preconditions;
import ${basepackage}.utils.dto.Result;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：${bizName}${table.sqlName}的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date ${now?string('yyyy年MM月dd日 HH:mm:ss')}
 */
@RestController
@Api(description = "${bizName}API")
public class ${className}Controller {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ${className}Service ${classNameLower}Service;

    @ApiOperation(value = "查询${bizName}的列表")
    @RequestMapping(value = "/api/admin/${bizPackage}/${classNameLower}/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        ${className} param = new ${className}();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
            <#if hasColumn(table.columns, "name")>
            param.setName(queryParams.getName());
            </#if>
        }
        Pager<${className}> pager = ${classNameLower}Service.page${className}(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新${bizName}")
    @RequestMapping(value = "/api/admin/${bizPackage}/${classNameLower}/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute ${className} pageObj) {
        ${className} dbObj = ${classNameLower}Service.saveOrUpdate(pageObj);
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除${bizName}", notes="根据主键id，删除${bizName}")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/${bizPackage}/${classNameLower}/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //${classNameLower}Service.remove${className}(ids);//逻辑删除
        ${classNameLower}Service.delete${className}(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取${bizName}", notes="根据主键id，获取${bizName}")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/${bizPackage}/${classNameLower}/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        ${className} dbObj = ${classNameLower}Service.get${className}(id);
        return Result.success("获取成功", dbObj);
    }

    <#-- 根据表的字段需要生成不同的方法 -->
    <#list table.columns as column>
    <#if column.columnNameLower == 'orderValue'>
    @ApiOperation(value="更新${bizName}的排序值", notes="根据主键id，更新${bizName}的排序值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderValue", value = "排序值", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/api/admin/${bizPackage}/${classNameLower}/updateOrderValue", method = RequestMethod.GET)
    public Result updateOrderValue(Long id, Long orderValue)
    {
        Preconditions.checkNotNull(id, "id不能为空");
        Preconditions.checkNotNull(orderValue, "排序值orderValue不能为空");
        ${classNameLower}Service.updateOrderValue(id, orderValue);
        return Result.success("更新成功");
    }
    </#if>
    </#list>

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/${bizPackage}/${classNameLower}/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = ${classNameLower}Service.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
