package com.lord.web.controller.sys;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.dto.sys.ExtendContentDto;
import com.lord.common.model.sys.SysExtendContent;
import com.lord.common.service.sys.SysExtendContentService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import com.lord.web.handler.UserHandler;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：扩展内容sys_extend_content的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月29日 20:18:20
 */
@RestController
@Api(description = "扩展内容API")
public class SysExtendContentController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysExtendContentService sysExtendContentService;

    @ApiOperation(value = "查询扩展内容的列表")
    @RequestMapping(value = "/api/admin/sys/sysExtendContent/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        SysExtendContent param = new SysExtendContent();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
        }
        Pager<SysExtendContent> pager = sysExtendContentService.pageSysExtendContent(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新扩展内容")
    @RequestMapping(value = "/api/admin/sys/sysExtendContent/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute SysExtendContent pageObj) {
        SysExtendContent dbObj = sysExtendContentService.saveOrUpdate(pageObj);
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除扩展内容", notes="根据主键id，删除扩展内容")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/sys/sysExtendContent/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //sysExtendContentService.removeSysExtendContent(ids);//逻辑删除
        sysExtendContentService.deleteSysExtendContent(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取扩展内容", notes="根据主键id，获取扩展内容")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/sys/sysExtendContent/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        SysExtendContent dbObj = sysExtendContentService.getSysExtendContent(id);
        return Result.success("获取成功", dbObj);
    }

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/sys/sysExtendContent/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = sysExtendContentService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }

    @ApiOperation(value = "获取实体的扩展内容", notes = "获取实体的扩展内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityCode", value = "实体编码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "entityId", value = "实体Id", dataType = "Long", paramType = "query")})
    @RequestMapping(value = "/api/admin/sys/getExtendContent", method = RequestMethod.GET)
    public Result getExtendContent(String entityCode, Long entityId) {
        Preconditions.checkNotNull(entityCode, "entityCode不能为空");
        SysExtendContent content = sysExtendContentService.getExtendContent(entityCode, entityId);
        return Result.success("获取实体的扩展内容成功", content);
    }
}
