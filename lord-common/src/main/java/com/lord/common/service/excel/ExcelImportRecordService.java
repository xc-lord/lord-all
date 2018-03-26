package com.lord.common.service.excel;

import com.lord.common.dto.PagerSort;
import com.lord.common.dto.Pager;
import com.lord.common.dto.PagerParam;
import com.lord.common.dto.excel.ExcelQueryParams;
import com.lord.common.dto.user.LoginUser;
import com.lord.common.model.excel.ExcelImportRecord;

/**
 * Excel导入记录excel_import_record的Service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月26日 11:31:45
 */
public interface ExcelImportRecordService {

    /**
     * 根据主键Id查询
     * @param id    主键Id
     * @return  数据库对象
     */
    ExcelImportRecord getExcelImportRecord(Long id);

    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @param loginUser
     * @return  数据库对象
     */
    ExcelImportRecord saveOrUpdate(ExcelImportRecord pageObj, LoginUser loginUser);

    /**
     * 分页查询
     * @param param     查询参数
     * @param page      当前页数
     * @param pageSize  分页大小
     * @return  分页结果
     */
    Pager<ExcelImportRecord> pageExcelImportRecord(ExcelQueryParams param, int page, int pageSize);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @return  分页结果
     */
    Pager<ExcelImportRecord> pageExcelImportRecord(ExcelQueryParams param, PagerParam pagerParam);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @param sorts         排序字段
     * @return  分页结果
     */
    Pager<ExcelImportRecord> pageExcelImportRecord(ExcelQueryParams param, PagerParam pagerParam, PagerSort... sorts);

    /**
     * 物理删除
     * @param ids    主键ID
     */
    void deleteExcelImportRecord(Long... ids);


    /**
     * 是否存在相同的记录
     * @param id        主键Id
     * @param rowName   属性名
     * @param rowValue  属性值
     * @return
     */
    boolean isExist(Long id, String rowName, String rowValue);

    /**
     * 导入Excel数据
     * @param id 记录ID
     */
    void importData(Long id);

    /**
     * 删除已经导入数据
     * @param id 记录ID
     */
    void deleteData(Long id);
}