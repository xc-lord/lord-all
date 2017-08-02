package com.lord.web.controller.cms;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.model.cms.CmsArticleTags;
import com.lord.common.service.cms.CmsArticleTagsService;
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
 * 功能：文章标签关联表cms_article_tags的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 18:03:02
 */
@RestController
@Api(description = "文章标签关联表API")
public class CmsArticleTagsController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CmsArticleTagsService cmsArticleTagsService;

    @ApiOperation(value = "查询文章标签关联表的列表")
    @RequestMapping(value = "/api/admin/cms/cmsArticleTags/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        CmsArticleTags param = new CmsArticleTags();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
        }
        Pager<CmsArticleTags> pager = cmsArticleTagsService.pageCmsArticleTags(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新文章标签关联表")
    @RequestMapping(value = "/api/admin/cms/cmsArticleTags/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute CmsArticleTags pageObj) {
        CmsArticleTags dbObj = cmsArticleTagsService.saveOrUpdate(pageObj);
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除文章标签关联表", notes="根据主键id，删除文章标签关联表")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/cms/cmsArticleTags/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //cmsArticleTagsService.removeCmsArticleTags(ids);//逻辑删除
        cmsArticleTagsService.deleteCmsArticleTags(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取文章标签关联表", notes="根据主键id，获取文章标签关联表")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/cms/cmsArticleTags/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        CmsArticleTags dbObj = cmsArticleTagsService.getCmsArticleTags(id);
        return Result.success("获取成功", dbObj);
    }


    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/cms/cmsArticleTags/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = cmsArticleTagsService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
