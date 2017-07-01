package com.lord.biz.dao.sys;

import com.lord.common.model.sys.SysSite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 站点sys_site的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 15:12:19
 */
@Repository
public interface SysSiteDao extends JpaRepository<SysSite, Long>, JpaSpecificationExecutor<SysSite> {

    @Modifying
    @Query("delete from SysSite where id in ?1")
    void deleteSysSite(Long... ids);

    @Query("select u from SysSite u where u.id in ?1")
    List<SysSite> findByIds(Long... ids);


    @Modifying
    @Query("update SysSite u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);
    @Modifying
    @Query("update SysSite u set u.removed = true where u.id in ?1")
    void removeSysSite(Long... id);

	//在此添加你的自定义方法...
}