package com.lord.biz.dao.sys;

import com.lord.common.model.sys.SysExtendTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 扩展属性模板sys_extend_template的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月09日 17:34:08
 */
@Repository
public interface SysExtendTemplateDao extends JpaRepository<SysExtendTemplate, Long>, JpaSpecificationExecutor<SysExtendTemplate> {

    @Modifying
    @Query("delete from SysExtendTemplate where id in ?1")
    void deleteSysExtendTemplate(Long... ids);

    @Query("select u from SysExtendTemplate u where u.id in ?1")
    List<SysExtendTemplate> findByIds(Long... ids);


    @Modifying
    @Query("update SysExtendTemplate u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);
    @Modifying
    @Query("update SysExtendTemplate u set u.removed = true where u.id in ?1")
    void removeSysExtendTemplate(Long... id);

	//在此添加你的自定义方法...
}