package com.lord.web.controller.ads;

import com.alibaba.fastjson.JSONObject;
import com.lord.common.dto.Pager;
import com.lord.common.dto.QueryParams;
import com.lord.common.dto.cat.TreeNode;
import com.lord.common.model.ads.AdsPage;
import com.lord.common.model.ads.AdsSpace;
import com.lord.common.service.RedisService;
import com.lord.common.service.ads.AdsPageService;
import com.lord.common.service.ads.AdsSpaceService;
import com.lord.common.service.ads.AdsTemplateService;
import com.lord.utils.Preconditions;
import com.lord.utils.dto.Result;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能：广告位ads_space的Api
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 11:12:10
 */
@RestController
@Api(description = "广告位API")
public class AdsSpaceController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AdsSpaceService adsSpaceService;

    @Autowired
    private AdsPageService adsPageService;

    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "查询广告位的树形列表")
    @RequestMapping(value = "/api/admin/ads/adsSpace/getTree", method = {RequestMethod.GET, RequestMethod.POST})
    public Result getTree(Long pageId) {
        Preconditions.checkArgument(pageId == null, "pageId不能为空");
        List<TreeNode> treeNodes = adsSpaceService.getTreeByPageId(pageId);
        return Result.success("查询成功", treeNodes);
    }

    @ApiOperation(value = "清除页面数据的缓存")
    @RequestMapping(value = "/api/admin/ads/adsSpace/removeCache", method = {RequestMethod.GET, RequestMethod.POST})
    public Result removeCache(Long pageId) {
        Preconditions.checkArgument(pageId == null, "pageId不能为空");
        adsSpaceService.removeCache(pageId);
        return Result.success("清除页面数据的缓存成功");
    }

    @ApiOperation(value = "查询广告位的列表")
    @RequestMapping(value = "/api/admin/ads/adsSpace/list", method = {RequestMethod.GET, RequestMethod.POST})
    public Result list(@ModelAttribute QueryParams queryParams) {
        AdsSpace param = new AdsSpace();
        if (queryParams != null) {
            //TODO:待修改
            param.setId(queryParams.getLongId());
            param.setName(queryParams.getName());
        }
        Pager<AdsSpace> pager = adsSpaceService.pageAdsSpace(param, queryParams.getPage(), queryParams.getPageSize());
        return Result.success("查询成功", pager);
    }

    @ApiOperation(value = "保存或更新广告位")
    @RequestMapping(value = "/api/admin/ads/adsSpace/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@ModelAttribute AdsSpace pageObj) {
        AdsSpace dbObj = adsSpaceService.saveOrUpdate(pageObj);
        redisService.delete(AdsTemplateService.ADS_ALL_PAGE + dbObj.getPageId());//更新缓存
        return Result.success("保存成功", dbObj);
    }

    @ApiOperation(value="删除广告位", notes="根据主键id，删除广告位")
    @ApiImplicitParam(name = "ids", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/ads/adsSpace/remove", method = RequestMethod.GET)
    public Result remove(Long[] ids) {
        Preconditions.checkNotNull(ids, "ids不能为空");
        //adsSpaceService.removeAdsSpace(ids);//逻辑删除
        adsSpaceService.deleteAdsSpace(ids);//物理删除
        return Result.success("删除成功");
    }

    @ApiOperation(value="获取广告位", notes="根据主键id，获取广告位")
    @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(value = "/api/admin/ads/adsSpace/{id}", method = RequestMethod.GET)
    public Result get(@PathVariable Long id) {
        Preconditions.checkNotNull(id, "id不能为空");
        AdsSpace dbObj = adsSpaceService.getAdsSpace(id);
        return Result.success("获取成功", dbObj);
    }

    @ApiOperation(value="获取广告位", notes="根据主键id，获取广告位")
    @ApiImplicitParam(name = "spaceId", value = "主键Id", required = true, dataType = "Long", paramType = "query")
    @RequestMapping(value = "/api/admin/ads/adsSpace/getInfo", method = RequestMethod.GET)
    public Result getInfo(Long spaceId) {
        Preconditions.checkNotNull(spaceId, "spaceId不能为空");
        AdsSpace adsSpace = adsSpaceService.getAdsSpace(spaceId);
        Preconditions.checkNotNull(adsSpace, "广告位不存在");
        AdsPage adsPage = adsPageService.getAdsPage(adsSpace.getPageId());
        JSONObject json = new JSONObject();
        json.put("adsPage", adsPage);
        json.put("adsSpace", adsSpace);
        return Result.success("获取成功", json);
    }

    @ApiOperation(value="更新广告位的排序值", notes="根据主键id，更新广告位的排序值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "orderValue", value = "排序值", required = true, dataType = "Long", paramType = "query")
    })
    @RequestMapping(value = "/api/admin/ads/adsSpace/updateOrderValue", method = RequestMethod.GET)
    public Result updateOrderValue(Long id, Long orderValue)
    {
        Preconditions.checkNotNull(id, "id不能为空");
        Preconditions.checkNotNull(orderValue, "排序值orderValue不能为空");
        adsSpaceService.updateOrderValue(id, orderValue);
        return Result.success("更新成功");
    }

    @ApiOperation(value = "判断记录是否存在", notes = "判断记录是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageId", value = "页面Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "parentId", value = "父节点Id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "rowName", value = "属性名", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rowValue", value = "属性值", dataType = "String", paramType = "query")})
    @RequestMapping(value = "/api/admin/ads/adsSpace/isExist", method = RequestMethod.GET)
    public Result isExist(Long id, Long pageId, Long parentId, String rowName, String rowValue) {
        Preconditions.checkNotNull(rowName, "rowName不能为空");
        Preconditions.checkNotNull(rowValue, "rowValue不能为空");
        boolean isRepeat = adsSpaceService.isExist(id, pageId, parentId, rowName, rowValue);
        if (isRepeat) {
            return Result.success("已经存在相同的记录", isRepeat);
        }
        return Result.failure("不存在相同的记录", isRepeat);
    }
}
