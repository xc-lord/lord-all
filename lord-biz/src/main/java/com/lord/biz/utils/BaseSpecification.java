package com.lord.biz.utils;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * 功能：基础的动态SQL拼装工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月08日 18:25
 */
public class BaseSpecification {

    /**
     * 根据属性名和对应的属性值去查询
     * @param rowName   属性名
     * @param rowValue  属性值
     * @param t <T>     表类型
     * @return
     */
    public static <T> Specification<T> queryBy(final String rowName, final Object rowValue, final Class<T> t) {
        return queryBy(rowName, rowValue, null, t);
    }

    /**
     * 根据属性名和对应的属性值去查询
     * @param rowName   属性名
     * @param rowValue  属性值
     * @param removed   是否屏蔽逻辑删除的数据，null时不屏蔽
     * @param t <T>     表类型
     * @return
     */
    public static <T> Specification<T> queryBy(final String rowName, final Object rowValue, final Boolean removed, final Class<T> t) {
        return new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Path row = root.get(rowName);
                Predicate predicate = builder.equal(row, rowValue);
                if (removed != null) {
                    predicate = builder.and(predicate, builder.equal(root.get("removed"), removed));
                }
                return predicate;
            }
        };
    }

}
