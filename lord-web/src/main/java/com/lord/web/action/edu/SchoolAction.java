package com.lord.web.action.edu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lord.common.dto.sys.DistrictDto;
import com.lord.common.model.edu.EduSchool;
import com.lord.common.model.sys.SysExtendAttribute;
import com.lord.common.model.sys.SysExtendContent;
import com.lord.common.service.edu.EduSchoolService;
import com.lord.common.service.sys.SysDistrictService;
import com.lord.common.service.sys.SysExtendAttributeService;
import com.lord.common.service.sys.SysExtendContentService;
import com.lord.utils.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 功能：学校的前端API
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月07日 21:40
 */
@RestController
@Api(description = "学校的前端API")
public class SchoolAction
{
    @Autowired
    private SysDistrictService sysDistrictService;

    @Autowired
    private EduSchoolService eduSchoolService;

    @Autowired
    private SysExtendAttributeService sysExtendAttributeService;

    @Autowired
    private SysExtendContentService sysExtendContentService;

    @ApiOperation(value="获取学校目录", notes="获取学校目录")
    @RequestMapping(value = "/api/edu/listSchool", method = RequestMethod.GET)
    public Result listSchool() {
        List<DistrictDto> list = sysDistrictService.listChildrenDistrict(100000L);
        JSONArray jsonArray = new JSONArray();
        for (DistrictDto district : list)
        {
            JSONObject obj = (JSONObject) JSON.toJSON(district);
            List<EduSchool> schools = eduSchoolService.listByProvinceId(district.getId());
            obj.put("schools", schools);
            jsonArray.add(obj);
        }
        return Result.success("获取成功", jsonArray);
    }

    @ApiOperation(value="获取学校详情", notes="获取学校详情")
    @ApiImplicitParam(name = "id", value = "学校Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/edu/getSchool", method = RequestMethod.GET)
    public Result getSchool(Long id)
    {
        EduSchool school = eduSchoolService.getEduSchool(id);
        Map<String,SysExtendAttribute> schoolAttr = sysExtendAttributeService.getMapByEntity("eduSchool", id);
        String[] entityCodeArr = new String[]{"eduSchoolRecruit","eduSchoolTestTime","eduSchoolCondition", "eduSchoolScoreLine"};
        Map<String, SysExtendContent> schoolContent = sysExtendContentService.getMapByEntity(id, entityCodeArr);
        JSONObject json = new JSONObject();
        json.put("school", school);
        json.put("schoolAttr", schoolAttr);
        json.put("schoolContent", schoolContent);
        return Result.success("获取成功", json);
    }
}
