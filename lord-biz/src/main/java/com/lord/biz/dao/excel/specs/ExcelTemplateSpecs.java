package com.lord.biz.dao.excel.specs;

import com.lord.biz.utils.BaseSpecification;
import com.lord.common.dto.excel.ExcelQueryParams;
import com.lord.common.model.excel.ExcelCategory;
import com.lord.common.model.excel.ExcelTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Excel模板配置excel_template的动态SQL拼装工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月16日 15:51:05
 */
public class ExcelTemplateSpecs extends BaseSpecification {

    /**
     * 根据查询条件，分页查询
     * @param pageObj   查询参数
     * @return
     */
    public static Specification<ExcelTemplate> queryByExcelTemplate(final ExcelQueryParams pageObj) {
        return new Specification<ExcelTemplate>() {
            public Predicate toPredicate(Root<ExcelTemplate> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Predicate predicate = builder.conjunction();//添加where 1=1条件
                if (pageObj.getId() != null) {
                    //ID存在时，按ID进行查询
                    Path id = root.get("id");
                    predicate = builder.and(predicate, builder.equal(id, pageObj.getId()));
                    return predicate;
                }
                if (pageObj.getCategoryId() != null)
                {
                    Join<ExcelTemplate, ExcelCategory> categoryJoin = root.join("category", JoinType.LEFT);
                    Path categoryId = categoryJoin.get("id");
                    predicate = builder.and(predicate, builder.equal(categoryId, pageObj.getCategoryId()));
                }
                if (StringUtils.isNotEmpty(pageObj.getExcelName()))
                {
                    Path excelName = root.get("excelName");
                    predicate = builder.and(predicate, builder.equal(excelName, pageObj.getExcelName()));
                }
                if (StringUtils.isNotEmpty(pageObj.getTableName()))
                {
                    Path tableName = root.get("tableName");
                    predicate = builder.and(predicate, builder.equal(tableName, pageObj.getTableName()));
                }
                query.where(predicate);
                query.orderBy(builder.desc(root.get("orderValue")), builder.desc(root.get("createTime")));
                return null;
            }
        };
    }

}
