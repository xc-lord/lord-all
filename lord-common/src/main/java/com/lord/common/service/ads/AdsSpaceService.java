package com.lord.common.service.ads;

import com.lord.common.dto.PagerSort;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.ads.AdsSpaceInfo;
import com.lord.common.dto.cat.TreeNode;
import com.lord.common.model.ads.AdsPage;
import com.lord.common.model.ads.AdsSpace;
import com.lord.common.service.cat.CategorySimpleService;

import java.util.List;
import java.util.Map;

/**
 * 广告位ads_space的Service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 11:12:10
 */
public interface AdsSpaceService extends CategorySimpleService {

    /**
     * 根据主键Id查询
     * @param id    主键Id
     * @return  数据库对象
     */
    AdsSpace getAdsSpace(Long id);

    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @return  数据库对象
     */
    AdsSpace saveOrUpdate(AdsSpace pageObj);

    /**
     * 分页查询
     * @param param     查询参数
     * @param page      当前页数
     * @param pageSize  分页大小
     * @return  分页结果
     */
    Pager<AdsSpace> pageAdsSpace(AdsSpace param, int page, int pageSize);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @return  分页结果
     */
    Pager<AdsSpace> pageAdsSpace(AdsSpace param, PagerParam pagerParam);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @param sorts         排序字段
     * @return  分页结果
     */
    Pager<AdsSpace> pageAdsSpace(AdsSpace param, PagerParam pagerParam, PagerSort... sorts);

    /**
     * 物理删除
     * @param ids    主键ID
     */
    void deleteAdsSpace(Long... ids);


    /**
     * 更新排序值
     * @param id            主键Id
     * @param orderValue    排序值
     */
    void updateOrderValue(Long id, Long orderValue);

    /**
     * 是否存在相同的记录
     * @param id        主键Id
     * @param pageId
     * @param parentId
     * @param rowName   属性名
     * @param rowValue  属性值   @return
     * */
    boolean isExist(Long id, Long pageId, Long parentId, String rowName, String rowValue);

    /**
     * 根据页面查找，页面的所有广告位树形结构
     * @param pageId    页面Id
     * @return 树
     */
    List<TreeNode> getTreeByPageId(Long pageId);

    /**
     * 获得广告位，不存在则创建
     * @param pageObj   页面对象
     * @return 广告位
     */
    AdsSpace getAndCreate(AdsSpace pageObj);

    /**
     * 根据页面，获得所有广告位和对应的元素
     *
     * @param adsPage 页面对象
     * @return 广告位
     */
    Map<String, AdsSpaceInfo> loadAllSpaceAndElementData(AdsPage adsPage);

    List<AdsSpace> listSpaceByKeywordStart(Long pageId, Long spaceId, String keyword);

    /**
     * 清除页面数据的缓存
     * @param pageId    页面ID
     */
    void removeCache(Long pageId);
}