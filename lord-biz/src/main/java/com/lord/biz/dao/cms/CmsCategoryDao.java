package com.lord.biz.dao.cms;

import com.lord.common.model.cms.CmsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文章分类cms_category的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 16:25:37
 */
@Repository
public interface CmsCategoryDao extends JpaRepository<CmsCategory, Long>, JpaSpecificationExecutor<CmsCategory> {

    @Modifying
    @Query("delete from CmsCategory where id in ?1")
    void deleteCmsCategory(Long... ids);

    @Query("select u from CmsCategory u where u.id in ?1")
    List<CmsCategory> findByIds(Long... ids);

    @Modifying
    @Query("update CmsCategory u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);

    @Modifying
    @Query("update CmsCategory u set u.removed = true where u.id in ?1")
    void removeCmsCategory(Long... id);

    @Query("select u.id from CmsCategory u where u.parentIds like ?1")
    List<Long> findAllChildrenIdsLike(String parentIdStr);

    @Modifying
    @Query("update CmsCategory u set u.childrenIds = ?1, u.leaf = ?2 where u.id = ?3")
    void updateChildrenIds(String childrenIds, boolean isLeaf, Long parentId);

    @Query("select u from CmsCategory u where u.parentId in ?1")
    List<CmsCategory> findParentByIds(Long[] parentIds);
}