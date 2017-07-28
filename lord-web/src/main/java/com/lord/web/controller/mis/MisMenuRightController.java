package com.lord.web.controller.mis;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.model.mis.MisMenuRight;
import com.lord.common.service.mis.MisMenuRightService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：后台菜单的具体权限mis_menu_right的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月28日 18:03:31
 */
@RestController
@Api(description = "后台菜单的具体权限API")
public class MisMenuRightController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MisMenuRightService misMenuRightService;

    @ApiOperation(value = "查询后台菜单的具体权限的列表")
    @RequestMapping(value = "/api/admin/mis/misMenuRight/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        MisMenuRight param = new MisMenuRight();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
            param.setName(queryParams.getName());
        }
        Pager<MisMenuRight> pager = misMenuRightService.pageMisMenuRight(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新后台菜单的具体权限")
    @RequestMapping(value = "/api/admin/mis/misMenuRight/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute MisMenuRight pageObj) {
        MisMenuRight dbObj = misMenuRightService.saveOrUpdate(pageObj);
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除后台菜单的具体权限", notes="根据主键id，删除后台菜单的具体权限")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/mis/misMenuRight/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //misMenuRightService.removeMisMenuRight(ids);//逻辑删除
        misMenuRightService.deleteMisMenuRight(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取后台菜单的具体权限", notes="根据主键id，获取后台菜单的具体权限")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/mis/misMenuRight/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        MisMenuRight dbObj = misMenuRightService.getMisMenuRight(id);
        return Result.success("获取成功", dbObj);
    }

    @ApiOperation(value="更新后台菜单的具体权限的排序值", notes="根据主键id，更新后台菜单的具体权限的排序值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderValue", value = "排序值", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/api/admin/mis/misMenuRight/updateOrderValue", method = RequestMethod.GET)
    public Result updateOrderValue(Long id, Long orderValue)
    {
        Preconditions.checkNotNull(id, "id不能为空");
        Preconditions.checkNotNull(orderValue, "排序值orderValue不能为空");
        misMenuRightService.updateOrderValue(id, orderValue);
        return Result.success("更新成功");
    }

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/mis/misMenuRight/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = misMenuRightService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
