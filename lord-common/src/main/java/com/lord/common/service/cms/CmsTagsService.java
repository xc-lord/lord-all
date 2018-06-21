package com.lord.common.service.cms;

import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.model.cms.CmsArticle;
import com.lord.common.model.cms.CmsTags;

import java.util.List;

/**
 * 文章标签cms_tags的Service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 15:42:51
 */
public interface CmsTagsService {

    /**
     * 根据主键Id查询
     * @param id    主键Id
     * @return  数据库对象
     */
    CmsTags getCmsTags(Long id);

    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @return  数据库对象
     */
    CmsTags saveOrUpdate(CmsTags pageObj);

    /**
     * 分页查询
     * @param param     查询参数
     * @param page      当前页数
     * @param pageSize  分页大小
     * @return  分页结果
     */
    Pager<CmsTags> pageCmsTags(CmsTags param, int page, int pageSize);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @return  分页结果
     */
    Pager<CmsTags> pageCmsTags(CmsTags param, PagerParam pagerParam);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @param sorts         排序字段
     * @return  分页结果
     */
    Pager<CmsTags> pageCmsTags(CmsTags param, PagerParam pagerParam, PagerSort... sorts);

    /**
     * 物理删除
     * @param ids    主键ID
     */
    void deleteCmsTags(Long... ids);


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
    void removeCmsTags(Long... ids);

    /**
     * 是否存在相同的记录
     * @param id        主键Id
     * @param rowName   属性名
     * @param rowValue  属性值
     * @return
     */
    boolean isExist(Long id, String rowName, String rowValue);

    /**
     * 根据文章获取标签列表
     * @param article   文章
     * @return  标签列表
     */
    List<CmsTags> listByArticle(CmsArticle article);

    /**
     * 获取随机推荐的标签
     * @param num   数量
     * @return 标签列表
     */
    List<CmsTags> listRandomTags(Integer num);
}