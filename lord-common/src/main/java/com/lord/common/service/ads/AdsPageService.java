package com.lord.common.service.ads;

import com.lord.common.dto.PagerSort;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.ads.AdsPageQuery;
import com.lord.common.model.ads.AdsPage;

/**
 * 页面ads_page的Service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 09:13:24
 */
public interface AdsPageService {

    /**
     * 根据主键Id查询
     * @param id    主键Id
     * @return  数据库对象
     */
    AdsPage getAdsPage(Long id);

    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @return  数据库对象
     */
    AdsPage saveOrUpdate(AdsPage pageObj);

    /**
     * 分页查询
     * @param param     查询参数
     * @param page      当前页数
     * @param pageSize  分页大小
     * @return  分页结果
     */
    Pager<AdsPage> pageAdsPage(AdsPage param, int page, int pageSize);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @return  分页结果
     */
    Pager<AdsPage> pageAdsPage(AdsPage param, PagerParam pagerParam);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @param sorts         排序字段
     * @return  分页结果
     */
    Pager<AdsPage> pageAdsPage(AdsPage param, PagerParam pagerParam, PagerSort... sorts);

    /**
     * 物理删除
     * @param ids    主键ID
     */
    void deleteAdsPage(Long... ids);


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
     * 根据条件查询页面
     * @param query
     * @return
     */
    Pager<AdsPage> pageAdsPage(AdsPageQuery query);

    /**
     * 根据编码和页面名称，获得页面信息，不存在则自动创建
     * @param page_code 页面编码
     * @param page_name 页面名称
     * @return 页面
     */
    AdsPage getAndCreate(String page_code, String page_name);

    /**
     * 根据页面编码查找页面
     * @param pageCode
     * @return
     */
    AdsPage loadAdsPage(String pageCode);

    /**
     * 更新页面的xml配置
     * @param id    页面Id
     * @param xml   xml配置
     */
    void updatePageConfig(Long id, String xml);
}