package com.lord.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lord.common.constant.BaseEnumType;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：全网站公共的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年04月15日 17:13:30
 */
@Api(description = "全网站公共的Api")
@RestController
public class CommonController
{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static Map<String, JSONArray> enumMaps = new HashMap<>();

    @ApiOperation(value = "获得枚举类型列表", notes = "根据类名，获得类型列表")
    @ApiImplicitParam(name = "cls", value = "类名，逗号分隔", required = true, dataType = "Array", paramType = "query")
    @RequestMapping(value = "/api/mis/getEnumType", method = RequestMethod.GET)
    public Result getEnumType(String[] cls)
    {
        Preconditions.checkNotNull(cls, "类名不能为空");
        Preconditions.checkArgument(cls.length < 1, "类名不能为空!");
        JSONObject jsonObject = new JSONObject();
        try
        {
            for (String className : cls)
            {
                String clsName = "";
                if (className.contains("_"))
                {
                    clsName = className.replaceAll("_", ".");
                }
                if (enumMaps.get(className) != null)
                {
                    jsonObject.put(className, enumMaps.get(className));
                    continue;
                }
                logger.info("使用反射加载{}的枚举类型列表", className);
                Class aClass = Class.forName("com.lord.common.constant." + clsName);
                Method method = aClass.getMethod("values");
                BaseEnumType inter[] = (BaseEnumType[]) method.invoke(null, null);
                JSONArray enumArray = new JSONArray();
                for (BaseEnumType enumValue : inter)
                {
                    JSONObject json = new JSONObject();
                    json.put("value", enumValue);
                    json.put("label", enumValue.getName());
                    enumArray.add(json);
                }
                enumMaps.put(className, enumArray);//保存到缓存中
                jsonObject.put(className, enumArray);
            }
        }
        catch (Exception e)
        {
            logger.error("根据类名，获得类型列表失败：" + e.getMessage(), e);
        }
        return Result.success(jsonObject);
    }
}
