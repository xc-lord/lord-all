package com.lord.common.service.cms;

import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.model.cms.CmsCategory;
import com.lord.common.service.cat.CategoryService;

import java.util.List;

/**
 * 文章分类cms_category的Service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 16:25:37
 */
public interface CmsCategoryService extends CategoryService {

    /**
     * 根据主键Id查询
     * @param id    主键Id
     * @return  数据库对象
     */
    CmsCategory getCmsCategory(Long id);

    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @return  数据库对象
     */
    CmsCategory saveOrUpdate(CmsCategory pageObj);

    /**
     * 分页查询
     * @param param     查询参数
     * @param page      当前页数
     * @param pageSize  分页大小
     * @return  分页结果
     */
    Pager<CmsCategory> pageCmsCategory(CmsCategory param, int page, int pageSize);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @return  分页结果
     */
    Pager<CmsCategory> pageCmsCategory(CmsCategory param, PagerParam pagerParam);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @param sorts         排序字段
     * @return  分页结果
     */
    Pager<CmsCategory> pageCmsCategory(CmsCategory param, PagerParam pagerParam, PagerSort... sorts);

    /**
     * 物理删除
     * @param ids    主键ID
     */
    void deleteCmsCategory(Long... ids);


    /**
     * 更新排序值
     * @param id            主键Id
     * @param orderValue    排序值
     */
    void updateOrderValue(Long id, Long orderValue);
    /**
     * 逻辑删除
     * @param ids    主键ID
     */
    void removeCmsCategory(Long... ids);

    /**
     * 是否存在相同的记录
     * @param id        主键Id
     * @param rowName   属性名
     * @param rowValue  属性值
     * @return
     */
    boolean isExist(Long id, String rowName, String rowValue);

    /**
     * 获取父分类列表
     * @param category  分类
     * @return
     */
    List<CmsCategory> listParentCategory(CmsCategory category);
}