package com.lord.biz.dao.sys;

import com.lord.common.model.sys.SysFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统菜单sys_file的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年06月26日 17:34:08
 */
@Repository
public interface SysFileDao extends JpaRepository<SysFile, Long>, JpaSpecificationExecutor<SysFile> {

    @Modifying
    @Query("delete from SysFile where id in ?1")
    void deleteSysFile(Long... ids);

    @Query("select u from SysFile u where u.id in ?1")
    List<SysFile> findByIds(Long... ids);


	//在此添加你的自定义方法...
}