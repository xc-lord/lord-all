package com.lord.biz.dao.mis.specs;

import com.lord.biz.utils.BaseSpecification;
import com.lord.common.model.mis.MisRoleRight;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * 角色权限管理mis_role_right的动态SQL拼装工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月01日 15:44:25
 */
public class MisRoleRightSpecs extends BaseSpecification {

    /**
     * 根据查询条件，分页查询
     * @param pageObj   查询参数
     * @return
     */
    public static Specification<MisRoleRight> queryByMisRoleRight(final MisRoleRight pageObj) {
        return new Specification<MisRoleRight>() {
            public Predicate toPredicate(Root<MisRoleRight> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path id = root.get("id");
                Predicate predicate = builder.conjunction();//添加where 1=1条件
                if (pageObj.getId() != null) {
                    //ID存在时，按ID进行查询
                    predicate = builder.and(predicate, builder.equal(id, pageObj.getId()));
                    return predicate;
                }
                //TODO:待修改
                query.where(predicate);
                return null;
            }
        };
    }

}
