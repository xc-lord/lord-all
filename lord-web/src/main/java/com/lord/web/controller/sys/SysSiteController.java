package com.lord.web.controller.sys;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.model.sys.SysSite;
import com.lord.common.service.sys.SysSiteService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：站点sys_site的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 15:12:19
 */
@RestController
@Api(description = "站点API")
public class SysSiteController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysSiteService sysSiteService;

    @ApiOperation(value = "查询站点的列表")
    @RequestMapping(value = "/api/admin/sys/sysSite/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        SysSite param = new SysSite();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
            param.setName(queryParams.getName());
        }
        Pager<SysSite> pager = sysSiteService.pageSysSite(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新站点")
    @RequestMapping(value = "/api/admin/sys/sysSite/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute SysSite pageObj) {
        SysSite dbObj = sysSiteService.saveOrUpdate(pageObj);
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除站点", notes="根据主键id，删除站点")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/sys/sysSite/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //sysSiteService.removeSysSite(ids);//逻辑删除
        sysSiteService.deleteSysSite(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取站点", notes="根据主键id，获取站点")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/sys/sysSite/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        SysSite dbObj = sysSiteService.getSysSite(id);
        return Result.success("获取成功", dbObj);
    }

    @ApiOperation(value="更新站点的排序值", notes="根据主键id，更新站点的排序值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderValue", value = "排序值", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/api/admin/sys/sysSite/updateOrderValue", method = RequestMethod.GET)
    public Result updateOrderValue(Long id, Long orderValue)
    {
        Preconditions.checkNotNull(id, "id不能为空");
        Preconditions.checkNotNull(orderValue, "排序值orderValue不能为空");
        sysSiteService.updateOrderValue(id, orderValue);
        return Result.success("更新成功");
    }

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/sys/sysSite/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = sysSiteService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
