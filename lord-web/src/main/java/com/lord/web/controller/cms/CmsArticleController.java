package com.lord.web.controller.cms;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.dto.cms.CmsArticleDto;
import com.lord.common.model.cms.CmsArticle;
import com.lord.common.service.cms.CmsArticleService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能：文章cms_article的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 18:09:10
 */
@RestController
@Api(description = "文章API")
public class CmsArticleController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CmsArticleService cmsArticleService;

    @ApiOperation(value = "查询文章的列表")
    @RequestMapping(value = "/api/admin/cms/cmsArticle/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        CmsArticle param = new CmsArticle();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
            if (CollectionUtils.isNotEmpty(queryParams.getIds()))
            {
                List<CmsArticle> list = cmsArticleService.listByIds(queryParams.getIds());
                Pager<CmsArticle> pager = new Pager<CmsArticle>(1, list.size(), list.size());
                pager.setList(list);
                return Result.success("查询成功", pager);
            }
        }
        Pager<CmsArticle> pager = cmsArticleService.pageCmsArticle(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新文章")
    @RequestMapping(value = "/api/admin/cms/cmsArticle/save", method = RequestMethod.POST)
    public Result save(@ModelAttribute CmsArticleDto pageObj) {
        CmsArticle dbObj = cmsArticleService.save(pageObj);
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value = "保存或更新文章")
    @RequestMapping(value = "/api/admin/cms/cmsArticle/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute CmsArticle pageObj) {
        CmsArticle dbObj = cmsArticleService.saveOrUpdate(pageObj);
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除文章", notes="根据主键id，删除文章")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/cms/cmsArticle/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //cmsArticleService.removeCmsArticle(ids);//逻辑删除
        cmsArticleService.deleteCmsArticle(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取文章", notes="根据主键id，获取文章")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/cms/cmsArticle/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        CmsArticle dbObj = cmsArticleService.getCmsArticle(id);
        return Result.success("获取成功", dbObj);
    }

    @ApiOperation(value="更新文章的排序值", notes="根据主键id，更新文章的排序值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderValue", value = "排序值", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/api/admin/cms/cmsArticle/updateOrderValue", method = RequestMethod.GET)
    public Result updateOrderValue(Long id, Long orderValue)
    {
        Preconditions.checkNotNull(id, "id不能为空");
        Preconditions.checkNotNull(orderValue, "排序值orderValue不能为空");
        cmsArticleService.updateOrderValue(id, orderValue);
        return Result.success("更新成功");
    }

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/cms/cmsArticle/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = cmsArticleService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
