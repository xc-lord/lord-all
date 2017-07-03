package com.lord.web.controller.cms;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.model.cms.CmsArticleRef;
import com.lord.common.service.cms.CmsArticleRefService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：关联文章表cms_article_ref的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 18:04:16
 */
@RestController
@Api(description = "关联文章表API")
public class CmsArticleRefController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CmsArticleRefService cmsArticleRefService;

    @ApiOperation(value = "查询关联文章表的列表")
    @RequestMapping(value = "/api/admin/cms/cmsArticleRef/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        CmsArticleRef param = new CmsArticleRef();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
        }
        Pager<CmsArticleRef> pager = cmsArticleRefService.pageCmsArticleRef(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新关联文章表")
    @RequestMapping(value = "/api/admin/cms/cmsArticleRef/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute CmsArticleRef pageObj) {
        CmsArticleRef dbObj = cmsArticleRefService.saveOrUpdate(pageObj);
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除关联文章表", notes="根据主键id，删除关联文章表")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/cms/cmsArticleRef/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //cmsArticleRefService.removeCmsArticleRef(ids);//逻辑删除
        cmsArticleRefService.deleteCmsArticleRef(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取关联文章表", notes="根据主键id，获取关联文章表")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/cms/cmsArticleRef/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        CmsArticleRef dbObj = cmsArticleRefService.getCmsArticleRef(id);
        return Result.success("获取成功", dbObj);
    }


    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/cms/cmsArticleRef/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = cmsArticleRefService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
