package com.lord.biz.dao.excel;

import com.lord.common.model.excel.ExcelTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Excel模板配置excel_template的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月16日 15:51:05
 */
@Repository
public interface ExcelTemplateDao extends JpaRepository<ExcelTemplate, Long>, JpaSpecificationExecutor<ExcelTemplate> {

    @Modifying
    @Query("delete from ExcelTemplate where id in ?1")
    void deleteExcelTemplate(Long... ids);

    @Query("select u from ExcelTemplate u where u.id in ?1")
    List<ExcelTemplate> findByIds(Long... ids);


    @Modifying
    @Query("update ExcelTemplate u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);

	//在此添加你的自定义方法...
}