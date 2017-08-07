package com.lord.web.controller.ads;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.model.ads.AdsElement;
import com.lord.common.service.RedisService;
import com.lord.common.service.ads.AdsElementService;
import com.lord.common.service.ads.AdsTemplateService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：广告位的元素ads_element的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 16:18:22
 */
@RestController
@Api(description = "广告位的元素API")
public class AdsElementController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AdsElementService adsElementService;

    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "查询广告位的元素的列表")
    @RequestMapping(value = "/api/admin/ads/adsElement/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        AdsElement param = new AdsElement();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
            param.setName(queryParams.getName());
            param.setSpaceId(queryParams.getExpandId());
        }
        Pager<AdsElement> pager = adsElementService.pageAdsElement(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新广告位的元素")
    @RequestMapping(value = "/api/admin/ads/adsElement/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute AdsElement pageObj) {
        AdsElement dbObj = adsElementService.saveOrUpdate(pageObj);
        redisService.delete(AdsTemplateService.ADS_ALL_SPACE + dbObj.getSpaceId());//更新广告位的缓存
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除广告位的元素", notes="根据主键id，删除广告位的元素")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/ads/adsElement/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //adsElementService.removeAdsElement(ids);//逻辑删除
        adsElementService.deleteAdsElement(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取广告位的元素", notes="根据主键id，获取广告位的元素")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/ads/adsElement/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        AdsElement dbObj = adsElementService.getAdsElement(id);
        return Result.success("获取成功", dbObj);
    }

    @ApiOperation(value="更新广告位的元素的排序值", notes="根据主键id，更新广告位的元素的排序值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderValue", value = "排序值", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/api/admin/ads/adsElement/updateOrderValue", method = RequestMethod.GET)
    public Result updateOrderValue(Long id, Long orderValue)
    {
        Preconditions.checkNotNull(id, "id不能为空");
        Preconditions.checkNotNull(orderValue, "排序值orderValue不能为空");
        adsElementService.updateOrderValue(id, orderValue);
        return Result.success("更新成功");
    }

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/ads/adsElement/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = adsElementService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
