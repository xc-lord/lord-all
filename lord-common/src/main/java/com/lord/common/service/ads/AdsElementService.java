package com.lord.common.service.ads;

import com.lord.common.dto.PagerSort;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.model.ads.AdsElement;
import com.lord.common.model.ads.AdsSpace;

/**
 * 广告位的元素ads_element的Service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 16:18:22
 */
public interface AdsElementService {

    /**
     * 根据主键Id查询
     * @param id    主键Id
     * @return  数据库对象
     */
    AdsElement getAdsElement(Long id);

    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @return  数据库对象
     */
    AdsElement saveOrUpdate(AdsElement pageObj);

    /**
     * 分页查询
     * @param param     查询参数
     * @param page      当前页数
     * @param pageSize  分页大小
     * @return  分页结果
     */
    Pager<AdsElement> pageAdsElement(AdsElement param, int page, int pageSize);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @return  分页结果
     */
    Pager<AdsElement> pageAdsElement(AdsElement param, PagerParam pagerParam);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @param sorts         排序字段
     * @return  分页结果
     */
    Pager<AdsElement> pageAdsElement(AdsElement param, PagerParam pagerParam, PagerSort... sorts);

    /**
     * 物理删除
     * @param ids    主键ID
     */
    void deleteAdsElement(Long... ids);


    /**
     * 更新排序值
     * @param id            主键Id
     * @param orderValue    排序值
     */
    void updateOrderValue(Long id, Long orderValue);

    /**
     * 是否存在相同的记录
     * @param id        主键Id
     * @param rowName   属性名
     * @param rowValue  属性值
     * @return
     */
    boolean isExist(Long id, String rowName, String rowValue);

    /**
     * 批量创建广告位元素数据，已存在则不创建
     * @param adsSpace   广告位
     */
    void batchCreateElement(AdsSpace adsSpace);
}