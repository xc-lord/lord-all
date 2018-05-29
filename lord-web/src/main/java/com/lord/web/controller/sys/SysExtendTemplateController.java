package com.lord.web.controller.sys;

import com.alibaba.fastjson.JSON;
import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.dto.sys.ExtendDetails;
import com.lord.common.dto.sys.ExtendTemplateDto;
import com.lord.common.model.sys.SysExtendAttr;
import com.lord.common.model.sys.SysExtendTemplate;
import com.lord.common.service.sys.SysExtendTemplateService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import com.lord.web.handler.UserHandler;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：扩展属性模板sys_extend_template的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月09日 17:34:08
 */
@RestController
@Api(description = "扩展属性模板API")
public class SysExtendTemplateController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysExtendTemplateService sysExtendTemplateService;

    @ApiOperation(value = "查询扩展属性模板的列表")
    @RequestMapping(value = "/api/admin/sys/sysExtendTemplate/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        SysExtendTemplate param = new SysExtendTemplate();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
            param.setName(queryParams.getName());
        }
        Pager<SysExtendTemplate> pager = sysExtendTemplateService.pageSysExtendTemplate(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新扩展属性模板")
    @RequestMapping(value = "/api/admin/sys/sysExtendTemplate/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute ExtendTemplateDto pageObj) {
        SysExtendTemplate dbObj = sysExtendTemplateService.saveOrUpdate(pageObj, UserHandler.getLoginUser());
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除扩展属性模板", notes="根据主键id，删除扩展属性模板")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/sys/sysExtendTemplate/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //sysExtendTemplateService.removeSysExtendTemplate(ids);//逻辑删除
        sysExtendTemplateService.deleteSysExtendTemplate(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取扩展属性模板", notes="根据主键id，获取扩展属性模板")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/sys/sysExtendTemplate/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        SysExtendTemplate dbObj = sysExtendTemplateService.getSysExtendTemplate(id);
        return Result.success("获取成功", dbObj);
    }

    @ApiOperation(value="获取扩展属性模板配置", notes="根据模板Id，获取模板配置")
    @ApiImplicitParam(name = "templateId", value = "模板Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/sys/sysExtendTemplate/getDetails", method = RequestMethod.GET)
    public Result getDetails(Long templateId) {
        Preconditions.checkNotNull(templateId, "id不能为空");
        SysExtendTemplate template = sysExtendTemplateService.getSysExtendTemplate(templateId);
        List<SysExtendAttr> columnList = sysExtendTemplateService.listSysExtendAttr(templateId);
        Map<String, Object> map = new HashMap<>();
        map.put("template", template);
        map.put("columnList", columnList);
        return Result.success("获取成功", map);
    }

    @ApiOperation(value="获取扩展属性模板配置", notes="根据实体编码，获取模板配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityCode", value = "实体编码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "entityId", value = "实体Id", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/api/admin/sys/getExtendDetails", method = RequestMethod.GET)
    public Result getExtendDetails(String entityCode, Long entityId) {
        Preconditions.checkNotNull(entityCode, "entityCode不能为空");
        ExtendDetails extendDetails = sysExtendTemplateService.getExtendDetails(entityCode, entityId);
        return Result.success("获取成功", extendDetails);
    }

    @ApiOperation(value = "保存或更新实体的扩展属性")
    @RequestMapping(value = "/api/admin/sys/saveExtendDetails", method = RequestMethod.POST)
    public Result saveExtendDetails(String extendJson)
    {
        ExtendDetails extendDetails = JSON.parseObject(extendJson, ExtendDetails.class);
        sysExtendTemplateService.saveExtendDetails(extendDetails, UserHandler.getLoginUser());
        return Result.success("保存扩展属性成功");
    }

    @ApiOperation(value="更新扩展属性模板的排序值", notes="根据主键id，更新扩展属性模板的排序值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderValue", value = "排序值", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/api/admin/sys/sysExtendTemplate/updateOrderValue", method = RequestMethod.GET)
    public Result updateOrderValue(Long id, Long orderValue)
    {
        Preconditions.checkNotNull(id, "id不能为空");
        Preconditions.checkNotNull(orderValue, "排序值orderValue不能为空");
        sysExtendTemplateService.updateOrderValue(id, orderValue);
        return Result.success("更新成功");
    }

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/sys/sysExtendTemplate/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = sysExtendTemplateService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
