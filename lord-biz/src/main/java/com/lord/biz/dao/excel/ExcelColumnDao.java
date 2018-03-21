package com.lord.biz.dao.excel;

import com.lord.common.model.excel.ExcelColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Excel列映射关系excel_column的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月20日 11:39:54
 */
@Repository
public interface ExcelColumnDao extends JpaRepository<ExcelColumn, Long>, JpaSpecificationExecutor<ExcelColumn> {

    @Modifying
    @Query("delete from ExcelColumn where id in ?1")
    void deleteExcelColumn(Long... ids);

    @Query("select u from ExcelColumn u where u.id in ?1")
    List<ExcelColumn> findByIds(Long... ids);

    @Modifying
    @Query("update ExcelColumn u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);

    @Query("select u from ExcelColumn u where u.excelTemplateId = ?1 order by orderValue")
    List<ExcelColumn> findByTemplateId(Long id);
}