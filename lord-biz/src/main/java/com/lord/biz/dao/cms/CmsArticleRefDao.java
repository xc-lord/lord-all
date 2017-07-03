package com.lord.biz.dao.cms;

import com.lord.common.model.cms.CmsArticleRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 关联文章表cms_article_ref的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 18:04:16
 */
@Repository
public interface CmsArticleRefDao extends JpaRepository<CmsArticleRef, Long>, JpaSpecificationExecutor<CmsArticleRef> {

    @Modifying
    @Query("delete from CmsArticleRef where id in ?1")
    void deleteCmsArticleRef(Long... ids);

    @Query("select u from CmsArticleRef u where u.id in ?1")
    List<CmsArticleRef> findByIds(Long... ids);


	//在此添加你的自定义方法...
}