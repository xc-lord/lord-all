package com.lord.biz.dao.excel.specs;

import com.lord.biz.utils.BaseSpecification;
import com.lord.common.model.excel.ExcelColumn;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Excel列映射关系excel_column的动态SQL拼装工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月20日 11:39:54
 */
public class ExcelColumnSpecs extends BaseSpecification {

    /**
     * 根据查询条件，分页查询
     * @param pageObj   查询参数
     * @return
     */
    public static Specification<ExcelColumn> queryByExcelColumn(final ExcelColumn pageObj) {
        return new Specification<ExcelColumn>() {
            public Predicate toPredicate(Root<ExcelColumn> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path id = root.get("id");
                Predicate predicate = builder.conjunction();//添加where 1=1条件
                if (pageObj.getId() != null) {
                    //ID存在时，按ID进行查询
                    predicate = builder.and(predicate, builder.equal(id, pageObj.getId()));
                    return predicate;
                }
                //TODO:待修改
                query.where(predicate);
                query.orderBy(builder.desc(root.get("orderValue")), builder.desc(root.get("createTime")));
                return null;
            }
        };
    }

}
