package com.lord.biz.dao.mis;

import com.lord.common.model.mis.MisUser;
import com.lord.common.model.mis.MisUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiaocheng on 2017/3/24.
 */
@Repository
public interface MisUserModelDao extends JpaRepository<MisUserModel, Long>, JpaSpecificationExecutor<MisUserModel> {
    MisUserModel findByUsername(String username);

    @Modifying
    @Query("update MisUser u set u.removed = true where u.id in ?1")
    void removeMisUser(Long[] id);

    @Modifying
    @Query("delete from MisUser where id in ?1")
    void deleteMisUser(Long[] id);

    @Modifying
    @Query("update MisUser u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);

    @Query("select u from MisUser u where u.id in ?1")
    List<MisUser> findByIds(Long[] ids);
}
