package com.lord.biz.dao.mis;

import com.lord.common.model.mis.MisMenuRight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 后台菜单的具体权限mis_menu_right的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月28日 18:03:31
 */
@Repository
public interface MisMenuRightDao extends JpaRepository<MisMenuRight, Long>, JpaSpecificationExecutor<MisMenuRight> {

    @Modifying
    @Query("delete from MisMenuRight where id in ?1")
    void deleteMisMenuRight(Long... ids);

    @Query("select u from MisMenuRight u where u.id in ?1")
    List<MisMenuRight> findByIds(Long... ids);


    @Modifying
    @Query("update MisMenuRight u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);

	//在此添加你的自定义方法...
}