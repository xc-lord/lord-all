package com.lord.web.controller.ads;

import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.dto.ads.AdsPageInfo;
import com.lord.common.dto.ads.AdsPageQuery;
import com.lord.common.model.ads.AdsPage;
import com.lord.common.service.RedisService;
import com.lord.common.service.ads.AdsPageService;
import com.lord.common.service.ads.AdsTemplateService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：页面ads_page的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 09:13:24
 */
@RestController
@Api(description = "页面API")
public class AdsPageController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AdsTemplateService adsTemplateService;

    @Autowired
    private AdsPageService adsPageService;

    @Autowired
    private RedisService redisService;

    @ApiOperation(value="获得页面数据", notes="根据页面编码，获得页面数据")
    @ApiImplicitParam(name = "pageCode", value = "页面编码", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/ads/getPage", method = RequestMethod.GET)
    public Result getPage(String pageCode) {
        Preconditions.checkNotNull(pageCode, "pageCode不能为空");
        AdsPageInfo pageInfo = adsTemplateService.getPageInfo(pageCode);
        return Result.success("获取成功", pageInfo);
    }

    @ApiOperation(value = "查询页面的列表")
    @RequestMapping(value = "/api/admin/ads/adsPage/list", method = { RequestMethod.GET, RequestMethod.POST })
    public Result list(@ModelAttribute AdsPageQuery query)
    {
        Pager<AdsPage> pager = adsPageService.pageAdsPage(query);
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "生成页面数据")
    @RequestMapping(value = "/api/admin/ads/adsPage/generateData", method = { RequestMethod.GET, RequestMethod.POST })
    public Result generateData(Long pageId)
    {
        AdsPage page = adsPageService.getAdsPage(pageId);
        Preconditions.checkNotNull(page, "页面" + pageId + "不存在");
        adsTemplateService.importData(page.getPageConfig());
        return Result.success("生成页面数据成功");
    }

    @ApiOperation(value = "保存或更新页面")
    @RequestMapping(value = "/api/admin/ads/adsPage/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute AdsPage pageObj) {
        AdsPage dbObj = adsPageService.saveOrUpdate(pageObj);
        redisService.delete(AdsTemplateService.ADS_ALL_PAGE_CODE + dbObj.getPageCode());//更新缓存
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除页面", notes="根据主键id，删除页面")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/ads/adsPage/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //adsPageService.removeAdsPage(ids);//逻辑删除
        adsPageService.deleteAdsPage(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取页面", notes="根据主键id，获取页面")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/ads/adsPage/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        AdsPage dbObj = adsPageService.getAdsPage(id);
        return Result.success("获取成功", dbObj);
    }

    @ApiOperation(value="更新页面的排序值", notes="根据主键id，更新页面的排序值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderValue", value = "排序值", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/api/admin/ads/adsPage/updateOrderValue", method = RequestMethod.GET)
    public Result updateOrderValue(Long id, Long orderValue)
    {
        Preconditions.checkNotNull(id, "id不能为空");
        Preconditions.checkNotNull(orderValue, "排序值orderValue不能为空");
        adsPageService.updateOrderValue(id, orderValue);
        return Result.success("更新成功");
    }

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/ads/adsPage/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = adsPageService.isExist(id, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
