package com.lord.web.controller.sys;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.model.sys.SysDistrict;
import com.lord.common.service.sys.SysDistrictService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能：行政区域sys_district的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年04月24日 15:04:09
 */
@RestController
@Api(description = "行政区域API")
public class SysDistrictController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysDistrictService sysDistrictService;

    @ApiOperation(value = "查询行政区域的列表")
    @RequestMapping(value = "/api/admin/sys/sysDistrict/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        SysDistrict param = new SysDistrict();
        if (queryParams != null) {
            param.setParentId(queryParams.getExpandId());
            param.setId(queryParams.getLongId());
            param.setName(queryParams.getName());
        }
        Pager<SysDistrict> pager = sysDistrictService.pageSysDistrict(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新行政区域")
    @RequestMapping(value = "/api/admin/sys/sysDistrict/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute SysDistrict pageObj) {
        SysDistrict dbObj = sysDistrictService.saveOrUpdate(pageObj);
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除行政区域", notes="根据主键id，删除行政区域")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/sys/sysDistrict/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //sysDistrictService.removeSysDistrict(ids);//逻辑删除
        sysDistrictService.deleteSysDistrict(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取行政区域", notes="根据主键id，获取行政区域")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/sys/sysDistrict/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        SysDistrict dbObj = sysDistrictService.getSysDistrict(id);
        return Result.success("获取成功", dbObj);
    }

    @ApiOperation(value="更新行政区域的排序值", notes="根据主键id，更新行政区域的排序值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderValue", value = "排序值", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/api/admin/sys/sysDistrict/updateOrderValue", method = RequestMethod.GET)
    public Result updateOrderValue(Long id, Long orderValue)
    {
        Preconditions.checkNotNull(id, "id不能为空");
        Preconditions.checkNotNull(orderValue, "排序值orderValue不能为空");
        sysDistrictService.updateOrderValue(id, orderValue);
        return Result.success("更新成功");
    }

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/sys/sysDistrict/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = sysDistrictService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }

    @ApiOperation(value="获取行政区域的所有父级区域", notes="根据主键id，获取行政区域的所有父级区域")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/sys/sysDistrict/listParentDistrict", method = RequestMethod.GET)
    public Result listParentDistrict(Long id)
    {
        Preconditions.checkNotNull(id, "id不能为空");
        List<SysDistrict> list = sysDistrictService.listParentDistrict(id);
        return Result.success("获取成功", list);
    }
}
