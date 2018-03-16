package com.lord.biz.dao.excel;

import com.lord.common.model.excel.ExcelCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Excel分类excel_category的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月16日 15:37:37
 */
@Repository
public interface ExcelCategoryDao extends JpaRepository<ExcelCategory, Long>, JpaSpecificationExecutor<ExcelCategory> {

    @Modifying
    @Query("delete from ExcelCategory where id in ?1")
    void deleteExcelCategory(Long... ids);

    @Query("select u from ExcelCategory u where u.id in ?1")
    List<ExcelCategory> findByIds(Long... ids);


    @Modifying
    @Query("update ExcelCategory u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);

	//在此添加你的自定义方法...
}