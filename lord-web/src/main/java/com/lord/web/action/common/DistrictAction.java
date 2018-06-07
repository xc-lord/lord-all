package com.lord.web.action.common;

import com.lord.common.dto.sys.DistrictDto;
import com.lord.common.service.sys.SysDistrictService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月07日 17:01
 */
@RestController
@Api(description = "行政区域前端API")
public class DistrictAction
{
    @Autowired
    private SysDistrictService sysDistrictService;

    @ApiOperation(value="获取子行政区域", notes="根据父区域Id，获取子行政区域")
    @ApiImplicitParam(name = "parentId", value = "父区域Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/common/listDistrict", method = RequestMethod.GET)
    public Result listDistrict(Long parentId) {
        if(parentId == null)
            parentId = 100000L;
        List<DistrictDto> list = sysDistrictService.listChildrenDistrict(parentId);
        return Result.success("获取成功", list);
    }

}
