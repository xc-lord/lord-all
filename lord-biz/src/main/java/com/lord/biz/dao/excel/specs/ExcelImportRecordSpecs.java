package com.lord.biz.dao.excel.specs;

import com.lord.biz.utils.BaseSpecification;
import com.lord.common.dto.excel.ExcelQueryParams;
import com.lord.common.model.excel.ExcelImportRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Excel导入记录excel_import_record的动态SQL拼装工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月26日 11:31:45
 */
public class ExcelImportRecordSpecs extends BaseSpecification {

    /**
     * 根据查询条件，分页查询
     * @param pageObj   查询参数
     * @return
     */
    public static Specification<ExcelImportRecord> queryByExcelImportRecord(final ExcelQueryParams pageObj) {
        return new Specification<ExcelImportRecord>() {
            public Predicate toPredicate(Root<ExcelImportRecord> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path id = root.get("id");
                Predicate predicate = builder.conjunction();//添加where 1=1条件
                if (pageObj.getId() != null) {
                    //ID存在时，按ID进行查询
                    predicate = builder.and(predicate, builder.equal(id, pageObj.getId()));
                    return predicate;
                }
                if (StringUtils.isNotEmpty(pageObj.getName())) {
                    Path name = root.get("name");
                    predicate = builder.and(predicate, builder.like(name, "%" + pageObj.getName() + "%"));
                }
                if (pageObj.getImportState() != null) {
                    Path importState = root.get("importState");
                    predicate = builder.and(predicate, builder.equal(importState, pageObj.getImportState()));
                }
                if (pageObj.getTemplateId() != null) {
                    Path templateId = root.get("templateId");
                    predicate = builder.and(predicate, builder.equal(templateId, pageObj.getTemplateId()));
                }
                query.where(predicate);
                query.orderBy(builder.desc(root.get("createTime")));
                return null;
            }
        };
    }

}
