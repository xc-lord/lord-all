package com.lord.biz.dao.sys;

import com.lord.common.model.sys.SysExtendAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 实体的扩展属性值sys_extend_attribute的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月22日 16:53:03
 */
@Repository
public interface SysExtendAttributeDao extends JpaRepository<SysExtendAttribute, Long>, JpaSpecificationExecutor<SysExtendAttribute> {

    @Modifying
    @Query("delete from SysExtendAttribute where id in ?1")
    void deleteSysExtendAttribute(Long... ids);

    @Query("select u from SysExtendAttribute u where u.id in ?1")
    List<SysExtendAttribute> findByIds(Long... ids);


	//在此添加你的自定义方法...
}