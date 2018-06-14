package com.lord.web.action.edu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lord.common.dto.cat.TreeNode;
import com.lord.common.dto.sys.DistrictDto;
import com.lord.common.model.cms.CmsCategory;
import com.lord.common.model.edu.EduSchool;
import com.lord.common.service.cms.CmsArticleService;
import com.lord.common.service.cms.CmsCategoryService;
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
    public Result listArticle(Long catId)
    {
        CmsCategory category = null;
        List<CmsCategory> categoryList = null;
        String parentCatIds = "";
        if(catId != null)
        {
            category = cmsCategoryService.getCmsCategory(catId);
            categoryList = cmsCategoryService.listParentCategory(category);
            for (CmsCategory cmsCategory : categoryList)
            {
                parentCatIds += cmsCategory.getId() + ",";
            }
        }
        List<TreeNode> treeNodes = cmsCategoryService.getTreeNodes();
        JSONObject json = new JSONObject();
        json.put("category", category);
        json.put("categoryList", categoryList);
        json.put("categoryTree", treeNodes);
        json.put("parentCatIds", parentCatIds);
        return Result.success("获取成功", json);
    }
}
