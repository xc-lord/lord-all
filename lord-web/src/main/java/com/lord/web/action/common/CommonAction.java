package com.lord.web.action.common;

import com.lord.utils.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 功能：公共的前端API
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月20日 19:48
 */
@RestController
@Api(description = "公共的前端API")
public class CommonAction
{
    @ApiOperation(value="提建议的保存接口", notes="提建议的保存接口")
    @RequestMapping(value = "/api/edu/advice", method = RequestMethod.POST)
    public Result advice(String content, String phone)
    {
        return Result.success("保存成功");
    }
}
