package com.lord.biz.dao.mis;

import com.lord.common.model.mis.MisMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统菜单mis_menu的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月10日 15:51:31
 */
@Repository
public interface MisMenuDao extends JpaRepository<MisMenu, Long>, JpaSpecificationExecutor<MisMenu> {

    @Modifying
    @Query("delete from MisMenu where id in ?1")
    void deleteMisMenu(Long... ids);

    @Query("select u from MisMenu u where u.id in ?1")
    List<MisMenu> findByIds(Long... ids);

    @Query("select u from MisMenu u where u.parentId in ?1")
    List<MisMenu> findParentByIds(Long... ids);

    @Modifying
    @Query("update MisMenu u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);

	//在此添加你的自定义方法...
}