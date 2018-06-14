package com.lord.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.lord.common.service.sys.SysFileService;
import com.lord.utils.dto.Result;
import com.lord.utils.exception.CommonException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SysFileService sysFileService;

    @ApiOperation(value="首页", notes="获取用户列表备注")
    @RequestMapping("/api/")
    public String home() {
        logger.info("spring boot hello world!");
        return "Hello World!首页";
    }

    @ApiOperation(value="服务状态", notes="服务状态")
    @RequestMapping("/api/health")
    public Result health()
    {
        JSONObject json = new JSONObject();
        json.put("server", 1);
        int dbState = 0;
        try
        {
            dbState = sysFileService.getDbState();
            json.put("db", dbState);
            return Result.success("服务正常", json);
        }
        catch (Exception e)
        {
            logger.error("数据库异常：" + e.getMessage(), e);
            return Result.failure("数据库异常：" + e.getMessage(), json);
        }
    }
}
