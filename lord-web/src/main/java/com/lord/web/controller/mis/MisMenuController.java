package com.lord.web.controller.mis;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.dto.TreeNode;
import com.lord.common.model.mis.MisMenu;
import com.lord.common.service.mis.MisMenuService;
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

import java.util.List;

/**
 * 功能：系统菜单mis_menu的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月10日 15:51:31
 */
@RestController
@Api(description = "系统菜单API")
public class MisMenuController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MisMenuService misMenuService;

    @ApiOperation(value = "查询系统菜单的树形列表")
    @RequestMapping(value = "/api/admin/mis/misMenu/getTree", method = {RequestMethod.GET, RequestMethod.POST})
    public Result getTree(@ModelAttribute QueryParams queryParams) {
        MisMenu param = new MisMenu();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
            param.setName(queryParams.getName());
        }
        List<TreeNode> treeNodes = misMenuService.getTreeNodes();
        return Result.success("查询成功", treeNodes);
    }

    @ApiOperation(value = "查询系统菜单的列表")
    @RequestMapping(value = "/api/admin/mis/misMenu/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        MisMenu param = new MisMenu();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
            param.setName(queryParams.getName());
        }
        Pager<MisMenu> pager = misMenuService.pageMisMenu(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新系统菜单")
    @RequestMapping(value = "/api/admin/mis/misMenu/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute MisMenu pageObj) {
        MisMenu dbObj = misMenuService.saveOrUpdate(pageObj);
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除系统菜单", notes="根据主键id，删除系统菜单")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/mis/misMenu/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //misMenuService.removeMisMenu(ids);//逻辑删除
        misMenuService.deleteMisMenu(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取系统菜单", notes="根据主键id，获取系统菜单")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/mis/misMenu/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        MisMenu dbObj = misMenuService.getMisMenu(id);
        return Result.success("获取成功", dbObj);
    }

    @ApiOperation(value="更新系统菜单的排序值", notes="根据主键id，更新系统菜单的排序值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderValue", value = "排序值", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/api/admin/mis/misMenu/updateOrderValue", method = RequestMethod.GET)
    public Result updateOrderValue(Long id, Long orderValue)
    {
        Preconditions.checkNotNull(id, "id不能为空");
        Preconditions.checkNotNull(orderValue, "排序值orderValue不能为空");
        misMenuService.updateOrderValue(id, orderValue);
        return Result.success("更新成功");
    }

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/mis/misMenu/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = misMenuService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
