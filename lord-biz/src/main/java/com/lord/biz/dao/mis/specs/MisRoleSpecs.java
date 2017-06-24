package com.lord.biz.dao.mis.specs;

import com.lord.biz.utils.BaseSpecification;
import com.lord.common.model.mis.MisRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * 功能：
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月08日 18:04
 */
public class MisRoleSpecs extends BaseSpecification {

    /**
     * 根据查询条件，分页查询
     * @param pageObj   查询参数
     * @return
     */
    public static Specification<MisRole> queryByMisRole(final MisRole pageObj) {
        return new Specification<MisRole>() {
            public Predicate toPredicate(Root<MisRole> root, CriteriaQuery<?> query,
                                         CriteriaBuilder builder) {
                Path id = root.get("id");
                //Predicate predicate = builder.conjunction();
                //存在逻辑删除判断
                Path removed = root.get("removed");
                Predicate predicate = builder.equal(removed, false);
                if (pageObj.getId() != null) {
                    //ID存在时，按ID进行查询
                    predicate = builder.and(predicate, builder.equal(id, pageObj.getId()));
                    return predicate;
                }
                if (StringUtils.isNotEmpty(pageObj.getName())) {
                    Path name = root.get("name");
                    predicate = builder.and(predicate, builder.like(name, "%" + pageObj.getName() + "%"));
                }
                query.where(predicate);
                query.orderBy(builder.desc(root.get("createTime")));
                return null;
            }
        };
    }

}
