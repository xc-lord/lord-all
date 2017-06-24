package com.lord.biz.dao.mis;

import com.lord.common.model.mis.MisRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色mis_role的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月08日 12:14:33
 */
@Repository
public interface MisRoleDao extends JpaRepository<MisRole, Long>, JpaSpecificationExecutor<MisRole> {

    @Modifying
    @Query("delete from MisRole where id in ?1")
    void deleteMisRole(Long... ids);

    @Query("select u from MisRole u where u.id in ?1")
    List<MisRole> findByIds(Long... ids);

    @Query("select u from MisRole u where u.roleCode=?1")
    List<MisRole> findByCode(String roleCode);

    @Modifying
    @Query("update MisRole u set u.removed = true where u.id in ?1")
    void removeMisRole(Long... id);

	//在此添加你的自定义方法...
}