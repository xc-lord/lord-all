package com.lord.biz.dao.ads;

import com.lord.common.model.ads.AdsPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 页面ads_page的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 09:13:24
 */
@Repository
public interface AdsPageDao extends JpaRepository<AdsPage, Long>, JpaSpecificationExecutor<AdsPage> {

    @Modifying
    @Query("delete from AdsPage where id in ?1")
    void deleteAdsPage(Long... ids);

    @Query("select u from AdsPage u where u.id in ?1")
    List<AdsPage> findByIds(Long... ids);


    @Modifying
    @Query("update AdsPage u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);

	//在此添加你的自定义方法...
}