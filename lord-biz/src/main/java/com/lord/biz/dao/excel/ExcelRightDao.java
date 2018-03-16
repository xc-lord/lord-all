package com.lord.biz.dao.excel;

import com.lord.common.model.excel.ExcelRight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Excel权限配置excel_right的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月16日 16:24:05
 */
@Repository
public interface ExcelRightDao extends JpaRepository<ExcelRight, Long>, JpaSpecificationExecutor<ExcelRight> {

    @Modifying
    @Query("delete from ExcelRight where id in ?1")
    void deleteExcelRight(Long... ids);

    @Query("select u from ExcelRight u where u.id in ?1")
    List<ExcelRight> findByIds(Long... ids);


	//在此添加你的自定义方法...
}