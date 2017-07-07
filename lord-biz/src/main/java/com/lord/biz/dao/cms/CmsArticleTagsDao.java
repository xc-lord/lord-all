package com.lord.biz.dao.cms;

import com.lord.common.model.cms.CmsArticleTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章标签关联表cms_article_tags的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 18:03:02
 */
@Repository
public interface CmsArticleTagsDao extends JpaRepository<CmsArticleTags, Long>, JpaSpecificationExecutor<CmsArticleTags> {

    @Modifying
    @Query("delete from CmsArticleTags where id in ?1")
    void deleteCmsArticleTags(Long... ids);

    @Query("select u from CmsArticleTags u where u.id in ?1")
    List<CmsArticleTags> findByIds(Long... ids);

    @Modifying
    @Query("delete from CmsArticleTags where articleId=?1")
    void deleteByArticle(Long id);
}