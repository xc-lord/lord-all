package com.lord.biz.dao.mis;

import com.lord.common.model.mis.MisRoleRight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色权限管理mis_role_right的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月01日 15:44:25
 */
@Repository
public interface MisRoleRightDao extends JpaRepository<MisRoleRight, Long>, JpaSpecificationExecutor<MisRoleRight> {

    @Modifying
    @Query("delete from MisRoleRight where id in ?1")
    void deleteMisRoleRight(Long... ids);

    @Query("select u from MisRoleRight u where u.id in ?1")
    List<MisRoleRight> findByIds(Long... ids);

    @Modifying
    @Query("delete from MisRoleRight where roleId = ?1 and menuRight=true")
    void deleteMenuRight(Long roleId);

    @Modifying
    @Query("delete from MisRoleRight where rightId = ?1")
    void deleteByRightId(Long rightId);

    List<MisRoleRight> findByRoleId(Long roleId);

    @Modifying
    @Query("delete from MisRoleRight where menuId in ?1")
    void deleteByMenuIds(Long[] ids);

    @Modifying
    @Query("delete from MisRoleRight where roleId in ?1")
    void deleteByRoleIds(Long[] ids);
}