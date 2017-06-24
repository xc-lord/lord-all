package com.lord.biz.dao.mis;

import com.lord.common.model.mis.MisUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 后台用户mis_user的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月09日 16:43:46
 */
@Repository
public interface MisUserDao extends JpaRepository<MisUser, Long>, JpaSpecificationExecutor<MisUser> {

    @Modifying
    @Query("delete from MisUser where id in ?1")
    void deleteMisUser(Long... ids);

    @Query("select u from MisUser u where u.id in ?1")
    List<MisUser> findByIds(Long... ids);

    @Modifying
    @Query("update MisUser u set u.removed = true where u.id in ?1")
    void removeMisUser(Long... id);

    @Modifying
    @Query("update MisUser u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);

    @Modifying
    @Query("update MisUser u set u.password = ?2 where u.id = ?1")
    void updatePassword(Long id, String password);

    //在此添加你的自定义方法...
}