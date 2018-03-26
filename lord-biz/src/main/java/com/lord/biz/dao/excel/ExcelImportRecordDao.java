package com.lord.biz.dao.excel;

import com.lord.common.model.excel.ExcelImportRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Excel导入记录excel_import_record的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月26日 11:31:45
 */
@Repository
public interface ExcelImportRecordDao extends JpaRepository<ExcelImportRecord, Long>, JpaSpecificationExecutor<ExcelImportRecord> {

    @Modifying
    @Query("delete from ExcelImportRecord where id in ?1")
    void deleteExcelImportRecord(Long... ids);

    @Query("select u from ExcelImportRecord u where u.id in ?1")
    List<ExcelImportRecord> findByIds(Long... ids);


	//在此添加你的自定义方法...
}