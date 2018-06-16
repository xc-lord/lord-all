package com.lord.web.action.edu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.cat.TreeNode;
import com.lord.common.dto.sys.DistrictDto;
import com.lord.common.model.cms.CmsArticle;
import com.lord.common.model.cms.CmsArticleContent;
import com.lord.common.model.cms.CmsCategory;
import com.lord.common.model.edu.EduSchool;
import com.lord.common.service.cms.CmsArticleService;
import com.lord.common.service.cms.CmsCategoryService;
import com.lord.utils.NavUtils;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：文章的前端API
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月12日 21:08
 */
@RestController
@Api(description = "文章的前端API")
public class ArticleAction
{
    @Autowired
    private CmsArticleService cmsArticleService;

    @Autowired
    private CmsCategoryService cmsCategoryService;

    @ApiOperation(value="获取文章列表", notes="获取文章列表")
    @RequestMapping(value = "/api/edu/listArticle", method = RequestMethod.GET)
    public Result listArticle(Long catId, Integer page, Integer pageSize)
    {
        CmsCategory category = null;
        List<CmsCategory> categoryList = null;
        String parentCatIds = "";
        if(catId != null)
        {
            category = cmsCategoryService.getCmsCategory(catId);
            Preconditions.checkNotNull(category, "分类" + catId + "不存在");
            categoryList = cmsCategoryService.listParentCategory(category);
            for (CmsCategory cmsCategory : categoryList)
            {
                parentCatIds += cmsCategory.getId() + ",";
            }
        }
        //文章列表
        Pager<CmsArticle> articleData = cmsArticleService.pageByCategory(category, new PagerParam(page, pageSize));
        articleData = new Pager<>(9, 10, 180L, articleData.getList());
        //分页信息
        List<Integer> pageNavs = NavUtils.getPageNav(articleData.getPage(), articleData.getTotalPage());
        articleData.setPageNavs(pageNavs);
        //分类树状结构
        List<TreeNode> treeNodes = cmsCategoryService.getTreeNodes();

        //返回值
        JSONObject json = new JSONObject();
        json.put("category", category);
        json.put("categoryList", categoryList);
        json.put("categoryTree", treeNodes);
        json.put("parentCatIds", parentCatIds);
        json.put("articleData", articleData);
        return Result.success("获取成功", json);
    }

    @ApiOperation(value="获取文章详情", notes="获取文章详情")
    @RequestMapping(value = "/api/edu/getArticle", method = RequestMethod.GET)
    public Result listArticle(Long articleId)
    {
        //文章
        Preconditions.checkNotNull(articleId, "文章" + articleId + "不能为空");
        CmsArticle article = cmsArticleService.getCmsArticle(articleId);
        Preconditions.checkNotNull(article, "文章" + articleId + "不存在");

        //分类
        CmsCategory category = cmsCategoryService.getCmsCategory(article.getCatId());
        Preconditions.checkNotNull(category, "分类" + article.getCatId() + "不存在");
        List<CmsCategory> categoryList = cmsCategoryService.listParentCategory(category);
        //上一篇
        CmsArticle prevArticle = cmsArticleService.getPrevArticle(article);
        //下一篇
        CmsArticle nextArticle = cmsArticleService.getNextArticle(article);

        //内容
        CmsArticleContent content = cmsArticleService.getArticleContent(articleId);

        //返回值
        JSONObject json = new JSONObject();
        json.put("article", article);
        json.put("content", content);
        json.put("categoryList", categoryList);
        json.put("categoryList", categoryList);
        json.put("prevArticle", prevArticle);
        json.put("nextArticle", nextArticle);
        return Result.success("获取成功", json);
    }
}
