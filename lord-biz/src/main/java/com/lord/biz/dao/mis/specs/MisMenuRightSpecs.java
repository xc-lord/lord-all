package com.lord.biz.dao.mis.specs;

import com.lord.biz.utils.BaseSpecification;
import com.lord.common.model.mis.MisMenuRight;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * 后台菜单的具体权限mis_menu_right的动态SQL拼装工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月28日 18:03:31
 */
public class MisMenuRightSpecs extends BaseSpecification {

    /**
     * 根据查询条件，分页查询
     * @param pageObj   查询参数
     * @return
     */
    public static Specification<MisMenuRight> queryByMisMenuRight(final MisMenuRight pageObj) {
        return new Specification<MisMenuRight>() {
            public Predicate toPredicate(Root<MisMenuRight> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path id = root.get("id");
                Predicate predicate = builder.conjunction();//添加where 1=1条件
                if (pageObj.getId() != null) {
                    //ID存在时，按ID进行查询
                    predicate = builder.and(predicate, builder.equal(id, pageObj.getId()));
                    return predicate;
                }
                //TODO:待修改
                if (StringUtils.isNotEmpty(pageObj.getName())) {
                    Path name = root.get("name");
                    predicate = builder.and(predicate, builder.like(name, "%" + pageObj.getName() + "%"));
                }
                query.where(predicate);
                return null;
            }
        };
    }

    public static Specification<MisMenuRight> queryByMenu(final Long menuId, final String rowName, final Object rowValue) {
        return new Specification<MisMenuRight>() {
            public Predicate toPredicate(Root<MisMenuRight> root, CriteriaQuery<?> query,
                    CriteriaBuilder builder) {
                Predicate predicate = builder.equal(root.get("menuId"), menuId);
                predicate = builder.and(predicate, builder.equal(root.get(rowName), rowValue));
                return predicate;
            }
        };
    }

}
