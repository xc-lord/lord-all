package com.lord.biz.dao.cms;

import com.lord.common.model.cms.CmsTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章标签cms_tags的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 15:42:51
 */
@Repository
public interface CmsTagsDao extends JpaRepository<CmsTags, Long>, JpaSpecificationExecutor<CmsTags> {

    @Modifying
    @Query("delete from CmsTags where id in ?1")
    void deleteCmsTags(Long... ids);

    @Query("select u from CmsTags u where u.id in ?1")
    List<CmsTags> findByIds(Long... ids);


    @Modifying
    @Query("update CmsTags u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);
    @Modifying
    @Query("update CmsTags u set u.removed = true where u.id in ?1")
    void removeCmsTags(Long... id);

	//在此添加你的自定义方法...
}