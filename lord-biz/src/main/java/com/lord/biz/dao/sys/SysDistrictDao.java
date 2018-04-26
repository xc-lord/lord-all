package com.lord.biz.dao.sys;

import com.lord.common.model.sys.SysDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 行政区域sys_district的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年04月24日 15:04:09
 */
@Repository
public interface SysDistrictDao extends JpaRepository<SysDistrict, Long>, JpaSpecificationExecutor<SysDistrict> {

    @Transactional
    @Modifying
    @Query("delete from SysDistrict where id in ?1")
    void deleteSysDistrict(Long... ids);

    @Query("select u from SysDistrict u where u.id in ?1")
    List<SysDistrict> findByIds(Long... ids);


    @Modifying
    @Query("update SysDistrict u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);

    List<SysDistrict> findByParentId(Long parentId);

    //在此添加你的自定义方法...
}