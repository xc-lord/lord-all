package com.lord.common.service.cms;

import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.PagerSort;
import com.lord.common.dto.cms.CmsArticleDto;
import com.lord.common.model.cms.CmsArticle;
import com.lord.common.model.cms.CmsArticleContent;
import com.lord.common.model.cms.CmsCategory;

import java.util.List;
import java.util.Map;

/**
 * 文章cms_article的Service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 18:09:10
 */
public interface CmsArticleService {

    /**
     * 根据主键Id查询
     * @param id    主键Id
     * @return  数据库对象
     */
    CmsArticle getCmsArticle(Long id);

    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @return  数据库对象
     */
    CmsArticle saveOrUpdate(CmsArticle pageObj);

    /**
     * 分页查询
     * @param param     查询参数
     * @param page      当前页数
     * @param pageSize  分页大小
     * @return  分页结果
     */
    Pager<CmsArticle> pageCmsArticle(CmsArticle param, int page, int pageSize);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @return  分页结果
     */
    Pager<CmsArticle> pageCmsArticle(CmsArticle param, PagerParam pagerParam);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @param sorts         排序字段
     * @return  分页结果
     */
    Pager<CmsArticle> pageCmsArticle(CmsArticle param, PagerParam pagerParam, PagerSort... sorts);

    /**
     * 物理删除
     * @param ids    主键ID
     */
    void deleteCmsArticle(Long... ids);


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
    void removeCmsArticle(Long... ids);

    /**
     * 是否存在相同的记录
     * @param id        主键Id
     * @param rowName   属性名
     * @param rowValue  属性值
     * @return
     */
    boolean isExist(Long id, String rowName, String rowValue);

    /**
     * 保存文章
     * @param pageObj   页面对象
     * @return
     */
    CmsArticle save(CmsArticleDto pageObj);

    /**
     * 根据Id列表查询文章
     * @param ids
     * @return
     */
    List<CmsArticle> listByIds(List<String> ids);

    /**
     * 分页查询文章ID
     * @param page
     * @param pageSize
     * @return
     */
    List<String> listArticleIds(int page, int pageSize);

    /**
     * 查询文章
     * @param ids
     * @return
     */
    Map<String,CmsArticle> findMapByIds(List<String> ids);

    /**
     * 根据分类查询文章列表，分类为空时，查询全部文章
     * @param category
     * @param pagerParam
     * @return
     */
    Pager<CmsArticle> pageByCategory(CmsCategory category, PagerParam pagerParam);

    /**
     * 获取文章内容
     * @param articleId 文章ID
     * @return 文章内容
     */
    CmsArticleContent getArticleContent(Long articleId);

    /**
     * 获取上一篇文章
     * @param article 文章
     * @return 上一篇文章
     */
    CmsArticle getPrevArticle(CmsArticle article);

    /**
     * 获取下一篇文章
     * @param article 文章
     * @return 下一篇文章
     */
    CmsArticle getNextArticle(CmsArticle article);

    /**
     * 获取随机推荐的文章
     * @param num   数量
     * @param catId 分类ID
     * @return  文章列表
     */
    List<CmsArticle> listRandomArticle(Integer num, Long catId);

    /**
     * 获取关联文章
     * @param articleId 文章ID
     * @return 关联文章
     */
    List<CmsArticle> listRefArticle(Long articleId);

    /**
     * 分页查询标签的文章列表
     * @param tagsId        标签ID
     * @param pagerParam    分页参数
     * @return  文章列表
     */
    Pager<CmsArticle> pageByTags(Long tagsId, PagerParam pagerParam);
}