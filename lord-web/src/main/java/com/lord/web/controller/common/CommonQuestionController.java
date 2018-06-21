package com.lord.web.controller.common;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.model.common.CommonQuestion;
import com.lord.common.service.common.CommonQuestionService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import com.lord.web.handler.UserHandler;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：常见问题common_question的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月16日 18:06:42
 */
@RestController
@Api(description = "常见问题API")
public class CommonQuestionController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CommonQuestionService commonQuestionService;

    @ApiOperation(value = "查询常见问题的列表")
    @RequestMapping(value = "/api/admin/common/commonQuestion/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        CommonQuestion param = new CommonQuestion();
        if (queryParams != null) {
            param.setId(queryParams.getLongId());
            param.setEntityCode(queryParams.getExpandCode());
            param.setEntityId(queryParams.getExpandId());
        }
        Pager<CommonQuestion> pager = commonQuestionService.pageCommonQuestion(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新常见问题")
    @RequestMapping(value = "/api/admin/common/commonQuestion/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute CommonQuestion pageObj) {
        CommonQuestion dbObj = commonQuestionService.saveOrUpdate(pageObj);
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除常见问题", notes="根据主键id，删除常见问题")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/common/commonQuestion/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //commonQuestionService.removeCommonQuestion(ids);//逻辑删除
        commonQuestionService.deleteCommonQuestion(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取常见问题", notes="根据主键id，获取常见问题")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/common/commonQuestion/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        CommonQuestion dbObj = commonQuestionService.getCommonQuestion(id);
        return Result.success("获取成功", dbObj);
    }

    @ApiOperation(value="更新常见问题的排序值", notes="根据主键id，更新常见问题的排序值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderValue", value = "排序值", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/api/admin/common/commonQuestion/updateOrderValue", method = RequestMethod.GET)
    public Result updateOrderValue(Long id, Long orderValue)
    {
        Preconditions.checkNotNull(id, "id不能为空");
        Preconditions.checkNotNull(orderValue, "排序值orderValue不能为空");
        commonQuestionService.updateOrderValue(id, orderValue);
        return Result.success("更新成功");
    }

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/common/commonQuestion/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = commonQuestionService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
