package com.lord.web.controller.cms;

import com.lord.common.dto.OptionNode;
import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.dto.TreeNode;
import com.lord.common.model.cms.CmsCategory;
import com.lord.common.service.cms.CmsCategoryService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能：文章分类cms_category的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 16:25:37
 */
@RestController
@Api(description = "文章分类API")
public class CmsCategoryController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CmsCategoryService cmsCategoryService;

    @ApiOperation(value = "查询系统菜单的树形列表")
    @RequestMapping(value = "/api/admin/cms/cmsCategory/getTree", method = {RequestMethod.GET, RequestMethod.POST})
    public Result getTree(@ModelAttribute QueryParams queryParams) {
        CmsCategory param = new CmsCategory();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
            param.setName(queryParams.getName());
        }
        List<TreeNode> treeNodes = cmsCategoryService.getTreeNodes();
        return Result.success("查询成功", treeNodes);
    }

    @ApiOperation(value = "查询系统菜单的级联选择器数据")
    @RequestMapping(value = "/api/admin/cms/cmsCategory/getOptions", method = {RequestMethod.GET, RequestMethod.POST})
    public Result getOptions(@ModelAttribute QueryParams queryParams) {
        CmsCategory param = new CmsCategory();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
            param.setName(queryParams.getName());
        }
        List<OptionNode> treeNodes = cmsCategoryService.getOptions();
        return Result.success("查询成功", treeNodes);
    }

    @ApiOperation(value = "查询文章分类的列表")
    @RequestMapping(value = "/api/admin/cms/cmsCategory/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        CmsCategory param = new CmsCategory();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
            param.setName(queryParams.getName());
        }
        Pager<CmsCategory> pager = cmsCategoryService.pageCmsCategory(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新文章分类")
    @RequestMapping(value = "/api/admin/cms/cmsCategory/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute CmsCategory pageObj) {
        CmsCategory dbObj = cmsCategoryService.saveOrUpdate(pageObj);
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除文章分类", notes="根据主键id，删除文章分类")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/cms/cmsCategory/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //cmsCategoryService.removeCmsCategory(ids);//逻辑删除
        cmsCategoryService.deleteCmsCategory(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取文章分类", notes="根据主键id，获取文章分类")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/cms/cmsCategory/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        CmsCategory dbObj = cmsCategoryService.getCmsCategory(id);
        return Result.success("获取成功", dbObj);
    }

    @ApiOperation(value="更新文章分类的排序值", notes="根据主键id，更新文章分类的排序值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderValue", value = "排序值", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/api/admin/cms/cmsCategory/updateOrderValue", method = RequestMethod.GET)
    public Result updateOrderValue(Long id, Long orderValue)
    {
        Preconditions.checkNotNull(id, "id不能为空");
        Preconditions.checkNotNull(orderValue, "排序值orderValue不能为空");
        cmsCategoryService.updateOrderValue(id, orderValue);
        return Result.success("更新成功");
    }

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/cms/cmsCategory/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = cmsCategoryService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
