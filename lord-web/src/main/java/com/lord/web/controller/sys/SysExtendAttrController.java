package com.lord.web.controller.sys;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.model.sys.SysExtendAttr;
import com.lord.common.service.sys.SysExtendAttrService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import com.lord.web.handler.UserHandler;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：扩展属性sys_extend_attr的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月09日 18:08:35
 */
@RestController
@Api(description = "扩展属性API")
public class SysExtendAttrController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysExtendAttrService sysExtendAttrService;

    @ApiOperation(value = "查询扩展属性的列表")
    @RequestMapping(value = "/api/admin/sys/sysExtendAttr/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        SysExtendAttr param = new SysExtendAttr();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
            param.setName(queryParams.getName());
        }
        Pager<SysExtendAttr> pager = sysExtendAttrService.pageSysExtendAttr(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新扩展属性")
    @RequestMapping(value = "/api/admin/sys/sysExtendAttr/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute SysExtendAttr pageObj) {
        SysExtendAttr dbObj = sysExtendAttrService.saveOrUpdate(pageObj);
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除扩展属性", notes="根据主键id，删除扩展属性")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/sys/sysExtendAttr/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //sysExtendAttrService.removeSysExtendAttr(ids);//逻辑删除
        sysExtendAttrService.deleteSysExtendAttr(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取扩展属性", notes="根据主键id，获取扩展属性")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/sys/sysExtendAttr/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        SysExtendAttr dbObj = sysExtendAttrService.getSysExtendAttr(id);
        return Result.success("获取成功", dbObj);
    }

    @ApiOperation(value="更新扩展属性的排序值", notes="根据主键id，更新扩展属性的排序值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderValue", value = "排序值", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/api/admin/sys/sysExtendAttr/updateOrderValue", method = RequestMethod.GET)
    public Result updateOrderValue(Long id, Long orderValue)
    {
        Preconditions.checkNotNull(id, "id不能为空");
        Preconditions.checkNotNull(orderValue, "排序值orderValue不能为空");
        sysExtendAttrService.updateOrderValue(id, orderValue);
        return Result.success("更新成功");
    }

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/sys/sysExtendAttr/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = sysExtendAttrService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
