package com.lord.biz.dao.cms;

import com.lord.common.model.cms.CmsArticleContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章内容表cms_article_content的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 18:06:04
 */
@Repository
public interface CmsArticleContentDao extends JpaRepository<CmsArticleContent, Long>, JpaSpecificationExecutor<CmsArticleContent> {

    @Modifying
    @Query("delete from CmsArticleContent where id in ?1")
    void deleteCmsArticleContent(Long... ids);

    @Query("select u from CmsArticleContent u where u.id in ?1")
    List<CmsArticleContent> findByIds(Long... ids);


	//在此添加你的自定义方法...
}