package com.lord.biz.dao.ads;

import com.lord.common.model.ads.AdsElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 广告位的元素ads_element的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 16:18:22
 */
@Repository
public interface AdsElementDao extends JpaRepository<AdsElement, Long>, JpaSpecificationExecutor<AdsElement> {

    @Modifying
    @Query("delete from AdsElement where id in ?1")
    void deleteAdsElement(Long... ids);

    @Query("select u from AdsElement u where u.id in ?1")
    List<AdsElement> findByIds(Long... ids);


    @Modifying
    @Query("update AdsElement u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);

    @Query("select u from AdsElement u where u.spaceId = ?1 and u.startTime < ?2 and u.endTime > ?2")
    List<AdsElement> listEffectElement(Long spaceId, Date now);

    List<AdsElement> findBySpaceId(Long spaceId);

    //在此添加你的自定义方法...
}