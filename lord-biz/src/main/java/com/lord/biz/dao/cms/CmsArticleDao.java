package com.lord.biz.dao.cms;

import com.lord.common.model.cms.CmsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章cms_article的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 18:09:10
 */
@Repository
public interface CmsArticleDao extends JpaRepository<CmsArticle, Long>, JpaSpecificationExecutor<CmsArticle> {

    @Modifying
    @Query("delete from CmsArticle where id in ?1")
    void deleteCmsArticle(Long... ids);

    @Query("select u from CmsArticle u where u.id in ?1")
    List<CmsArticle> findByIds(Long... ids);

    @Modifying
    @Query("update CmsArticle u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);

    @Modifying
    @Query("update CmsArticle u set u.removed = true where u.id in ?1")
    void removeCmsArticle(Long... id);
}