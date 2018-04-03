package com.lord.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.lord.utils.dto.Result;
import com.lord.utils.exception.CommonException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by xiaocheng on 2017/3/23.
 */
@RestController
public class IndexController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @ApiOperation(value="首页", notes="获取用户列表备注")
    @RequestMapping("/api/")
    public String home() {
        logger.info("spring boot hello world!");
        return "Hello World!首页";
    }

    @RequestMapping("/api/testError")
    public String error() {
        if(true)
            throw new CommonException("运行错误");
        return "error";
    }

    @ApiOperation(value = "获取用户列表", notes = "获取用户列表备注")
    @ApiImplicitParam(name = "myName", value = "用户ID", required = true, dataType = "String", paramType = "path")
    @RequestMapping("/api/hello/{myName}")
    public Result index(@PathVariable String myName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 1);
        jsonObject.put("isShow", true);
        jsonObject.put("name", myName);
        jsonObject.put("createTime", new Date());
        return Result.success("请求成功", jsonObject);
    }
}
