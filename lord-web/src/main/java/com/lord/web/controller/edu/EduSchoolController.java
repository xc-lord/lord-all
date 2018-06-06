package com.lord.web.controller.edu;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.model.edu.EduSchool;
import com.lord.common.service.edu.EduSchoolService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import com.lord.web.handler.UserHandler;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：学校edu_school的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月06日 10:15:50
 */
@RestController
@Api(description = "学校API")
public class EduSchoolController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EduSchoolService eduSchoolService;

    @ApiOperation(value = "查询学校的列表")
    @RequestMapping(value = "/api/admin/edu/eduSchool/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        EduSchool param = new EduSchool();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
            param.setName(queryParams.getName());
        }
        Pager<EduSchool> pager = eduSchoolService.pageEduSchool(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新学校")
    @RequestMapping(value = "/api/admin/edu/eduSchool/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute EduSchool pageObj) {
        EduSchool dbObj = eduSchoolService.saveOrUpdate(pageObj, UserHandler.getLoginUser());
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除学校", notes="根据主键id，删除学校")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/edu/eduSchool/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //eduSchoolService.removeEduSchool(ids);//逻辑删除
        eduSchoolService.deleteEduSchool(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取学校", notes="根据主键id，获取学校")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/edu/eduSchool/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        EduSchool dbObj = eduSchoolService.getEduSchool(id);
        return Result.success("获取成功", dbObj);
    }

    @ApiOperation(value="更新学校的排序值", notes="根据主键id，更新学校的排序值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderValue", value = "排序值", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/api/admin/edu/eduSchool/updateOrderValue", method = RequestMethod.GET)
    public Result updateOrderValue(Long id, Long orderValue)
    {
        Preconditions.checkNotNull(id, "id不能为空");
        Preconditions.checkNotNull(orderValue, "排序值orderValue不能为空");
        eduSchoolService.updateOrderValue(id, orderValue);
        return Result.success("更新成功");
    }

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/edu/eduSchool/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = eduSchoolService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
