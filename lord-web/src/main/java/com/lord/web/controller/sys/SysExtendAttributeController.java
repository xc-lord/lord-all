package com.lord.web.controller.sys;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.model.sys.SysExtendAttribute;
import com.lord.common.service.sys.SysExtendAttributeService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import com.lord.web.handler.UserHandler;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：实体的扩展属性值sys_extend_attribute的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月22日 16:53:03
 */
@RestController
@Api(description = "实体的扩展属性值API")
public class SysExtendAttributeController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysExtendAttributeService sysExtendAttributeService;

    @ApiOperation(value = "查询实体的扩展属性值的列表")
    @RequestMapping(value = "/api/admin/sys/sysExtendAttribute/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        SysExtendAttribute param = new SysExtendAttribute();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
        }
        Pager<SysExtendAttribute> pager = sysExtendAttributeService.pageSysExtendAttribute(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新实体的扩展属性值")
    @RequestMapping(value = "/api/admin/sys/sysExtendAttribute/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute SysExtendAttribute pageObj) {
        SysExtendAttribute dbObj = sysExtendAttributeService.saveOrUpdate(pageObj);
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除实体的扩展属性值", notes="根据主键id，删除实体的扩展属性值")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/sys/sysExtendAttribute/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //sysExtendAttributeService.removeSysExtendAttribute(ids);//逻辑删除
        sysExtendAttributeService.deleteSysExtendAttribute(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取实体的扩展属性值", notes="根据主键id，获取实体的扩展属性值")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/sys/sysExtendAttribute/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        SysExtendAttribute dbObj = sysExtendAttributeService.getSysExtendAttribute(id);
        return Result.success("获取成功", dbObj);
    }


    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/sys/sysExtendAttribute/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = sysExtendAttributeService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
