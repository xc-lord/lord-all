package com.lord.web.controller.common;

import com.lord.common.dto.common.EntityDto;
import com.lord.common.model.cms.CmsArticle;
import com.lord.common.model.edu.EduSchool;
import com.lord.common.service.cms.CmsArticleService;
import com.lord.common.service.edu.EduSchoolService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能：公共实体的API
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月18日 09:48
 */
@RestController
@Api(description = "公共实体的API")
public class EntityController
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EduSchoolService eduSchoolService;

    @Autowired
    private CmsArticleService cmsArticleService;

    @ApiOperation(value = "获取实体信息", notes = "获取实体信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entityCode", value = "实体编码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "entityId", value = "实体Id", dataType = "Long", paramType = "query")})
    @RequestMapping(value = "/api/admin/common/getEntity", method = RequestMethod.GET)
    public Result getEntity(String entityCode, Long entityId) {
        Preconditions.checkNotNull(entityId, "entityId不能为空");
        Preconditions.checkNotNull(entityCode, "entityCode不能为空");
        EntityDto entityDto = new EntityDto();
        entityDto.setEntityCode(entityCode);
        entityDto.setId(entityId);

        switch (entityCode) {
        case "eduSchool":
            EduSchool eduSchool = eduSchoolService.getEduSchool(entityId);
            if(eduSchool == null) break;
            entityDto.setName(eduSchool.getName());
            break;
        case "cmsArticle":
            CmsArticle article = cmsArticleService.getCmsArticle(entityId);
            if(article == null) break;
            entityDto.setName(article.getTitle());
            break;
        }
        return Result.success("获取成功", entityDto);
    }
}
