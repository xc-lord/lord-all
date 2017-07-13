package com.lord.common.service.cms;

import com.lord.common.dto.PagerSort;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.model.cms.CmsArticleContent;

/**
 * 文章内容cms_article_content的Service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月08日 15:04:19
 */
public interface CmsArticleContentService {

    /**
     * 根据主键Id查询
     * @param id    主键Id
     * @return  数据库对象
     */
    CmsArticleContent getCmsArticleContent(Long id);

    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @return  数据库对象
     */
    CmsArticleContent saveOrUpdate(CmsArticleContent pageObj);

    /**
     * 分页查询
     * @param param     查询参数
     * @param page      当前页数
     * @param pageSize  分页大小
     * @return  分页结果
     */
    Pager<CmsArticleContent> pageCmsArticleContent(CmsArticleContent param, int page, int pageSize);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @return  分页结果
     */
    Pager<CmsArticleContent> pageCmsArticleContent(CmsArticleContent param, PagerParam pagerParam);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @param sorts         排序字段
     * @return  分页结果
     */
    Pager<CmsArticleContent> pageCmsArticleContent(CmsArticleContent param, PagerParam pagerParam, PagerSort... sorts);

    /**
     * 物理删除
     * @param ids    主键ID
     */
    void deleteCmsArticleContent(Long... ids);


    /**
     * 是否存在相同的记录
     * @param id        主键Id
     * @param rowName   属性名
     * @param rowValue  属性值
     * @return
     */
    boolean isExist(Long id, String rowName, String rowValue);

    /**
     * 根据文章Id，查询文章内容
     * @param articleId 文章Id
     * @return  文章内容
     */
    CmsArticleContent getByArticleId(String articleId);
}