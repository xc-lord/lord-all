package com.lord.web.controller.mis;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.model.mis.MisRole;
import com.lord.common.service.mis.MisRoleService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：用户角色mis_role的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月08日 12:14:33
 */
@RestController
@Api(description = "用户角色API")
public class MisRoleController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MisRoleService misRoleService;

    @ApiOperation(value = "查询用户角色的列表")
    @RequestMapping(value = "/api/admin/mis/misRole/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        MisRole param = new MisRole();
        if (queryParams != null) {
            param.setId(queryParams.getLongId());
            param.setName(queryParams.getName());
        }
        Pager<MisRole> pager = misRoleService.pageMisRole(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value="保存用户角色的权限", notes="保存用户角色的权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id", required = true, dataType = "Long", paramType = "form"),
            @ApiImplicitParam(name = "rightIds", value = "权限Ids", required = true, dataType = "Long", paramType = "form"),
            @ApiImplicitParam(name = "check", value = "是否添加", required = true, dataType = "Boolean", paramType = "form"),
    })
    @RequestMapping(value = "/api/admin/mis/misRole/saveRight", method = RequestMethod.POST)
    public Result saveRight(Long roleId, Long rightId, Boolean check) {
        Preconditions.checkNotNull(roleId, "roleId不能为空");
        Preconditions.checkNotNull(rightId, "rightId不能为空");
        Preconditions.checkNotNull(check, "check不能为空");
        misRoleService.saveRight(roleId, rightId, check);
        String msg = check ? "添加" : "删除";
        return Result.success(msg + "权限成功");
    }

    @ApiOperation(value="保存用户角色的菜单权限", notes="保存用户角色的菜单权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id", required = true, dataType = "Long", paramType = "form"),
            @ApiImplicitParam(name = "menuIds", value = "菜单Ids", required = true, dataType = "Long", paramType = "form"),
    })
    @RequestMapping(value = "/api/admin/mis/misRole/saveMenuRight", method = RequestMethod.POST)
    public Result saveMenuRight(Long roleId, Long[] menuIds) {
        Preconditions.checkNotNull(roleId, "roleId不能为空");
        Preconditions.checkNotNull(menuIds, "menuIds不能为空");
        misRoleService.saveMenuRight(roleId, menuIds);
        return Result.success("保存菜单权限成功");
    }

    @ApiOperation(value = "保存或更新用户角色")
    @RequestMapping(value = "/api/admin/mis/misRole/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute MisRole pageObj) {
        MisRole dbObj = misRoleService.saveOrUpdate(pageObj);
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除用户角色", notes="根据主键id，删除用户角色")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/mis/misRole/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        misRoleService.removeMisRole(ids);//逻辑删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取用户角色", notes="根据主键id，获取用户角色")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/mis/misRole/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        MisRole dbObj = misRoleService.getMisRole(id);
        return Result.success("获取成功", dbObj);
    }

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/mis/misRole/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = misRoleService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }

}
