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
    @ApiOperation(value="获取公共页头和页尾数据", notes="获取公共页头和页尾数据")
    @RequestMapping(value = "/api/edu/getEduCommonData", method = RequestMethod.GET)
    public Result getEduCommonData(Integer num, Long catId)
    {
        return Result.success("获取成功");
    }
}
