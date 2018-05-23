package com.lord.biz.dao.sys;

import com.lord.common.model.sys.SysExtendAttr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 扩展属性sys_extend_attr的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月09日 18:08:35
 */
@Repository
public interface SysExtendAttrDao extends JpaRepository<SysExtendAttr, Long>, JpaSpecificationExecutor<SysExtendAttr> {

    @Modifying
    @Query("delete from SysExtendAttr where id in ?1")
    void deleteSysExtendAttr(Long... ids);

    @Query("select u from SysExtendAttr u where u.id in ?1")
    List<SysExtendAttr> findByIds(Long... ids);


    @Modifying
    @Query("update SysExtendAttr u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);

    @Modifying
    @Query("delete from SysExtendAttr where templateId = ?1")
    void deleteByTemplateId(Long templateId);

    List<SysExtendAttr> findByTemplateId(Long templateId);

}