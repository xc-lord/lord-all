package com.lord.biz.dao.sys;

import com.lord.common.model.sys.SysExtendContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 扩展内容sys_extend_content的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月29日 20:18:20
 */
@Repository
public interface SysExtendContentDao extends JpaRepository<SysExtendContent, Long>, JpaSpecificationExecutor<SysExtendContent> {

    @Modifying
    @Query("delete from SysExtendContent where id in ?1")
    void deleteSysExtendContent(Long... ids);

    @Query("select u from SysExtendContent u where u.id in ?1")
    List<SysExtendContent> findByIds(Long... ids);

    SysExtendContent findOneByEntityCodeAndEntityId(String entityCode, Long entityId);

    @Query("select u from SysExtendContent u where u.entityId=?1 and u.entityCode in ?2")
    List<SysExtendContent> findByEntity(Long entityId, String... entityCodes);

    //在此添加你的自定义方法...
}