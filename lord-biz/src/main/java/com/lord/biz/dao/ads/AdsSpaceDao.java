package com.lord.biz.dao.ads;

import com.lord.common.model.ads.AdsSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 广告位ads_space的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 11:12:10
 */
@Repository
public interface AdsSpaceDao extends JpaRepository<AdsSpace, Long>, JpaSpecificationExecutor<AdsSpace> {

    @Modifying
    @Query("delete from AdsSpace where id in ?1")
    void deleteAdsSpace(Long... ids);

    @Query("select u from AdsSpace u where u.id in ?1")
    List<AdsSpace> findByIds(Long... ids);

    @Modifying
    @Query("update AdsSpace u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);

    @Query("select u from AdsSpace u where u.parentId in ?1")
    List<AdsSpace> findParentByIds(Long[] ids);

    @Query("select count(1) from AdsSpace u where u.pageId in ?1")
    long countByAdsPageIds(Long[] ids);

    @Query("select u from AdsSpace u where u.pageId = ?1 order by u.level, u.orderValue")
    List<AdsSpace> findAllByPageId(Long pageId);

    AdsSpace findByPageIdAndSubKeyword(Long id, String subKeyword);

    AdsSpace findByParentIdAndSubKeyword(Long parentId, String subKeyword);

    List<AdsSpace> findByPageIdAndParentIdAndKeywordLike(Long pageId, Long spaceId, String keyword);

    //在此添加你的自定义方法...
}