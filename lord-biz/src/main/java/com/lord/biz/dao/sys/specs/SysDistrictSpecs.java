package com.lord.biz.dao.sys.specs;

import com.lord.biz.utils.BaseSpecification;
import com.lord.common.model.sys.SysDistrict;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * 行政区域sys_district的动态SQL拼装工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年04月24日 15:04:09
 */
public class SysDistrictSpecs extends BaseSpecification {

    /**
     * 根据查询条件，分页查询
     * @param pageObj   查询参数
     * @return
     */
    public static Specification<SysDistrict> queryBySysDistrict(final SysDistrict pageObj) {
        return new Specification<SysDistrict>() {
            public Predicate toPredicate(Root<SysDistrict> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path id = root.get("id");
                Predicate predicate = builder.conjunction();//添加where 1=1条件
                if (pageObj.getId() != null) {
                    //ID存在时，按ID进行查询
                    predicate = builder.and(predicate, builder.equal(id, pageObj.getId()));
                    return predicate;
                }
                if (pageObj.getParentId() != null) {
                    Path parentId = root.get("parentId");
                    predicate = builder.and(predicate, builder.equal(parentId, pageObj.getParentId()));
                }
                if (StringUtils.isNotEmpty(pageObj.getName())) {
                    Path name = root.get("name");
                    predicate = builder.and(predicate, builder.like(name, "%" + pageObj.getName() + "%"));
                }
                query.where(predicate);
                query.orderBy(builder.asc(root.get("orderValue")), builder.desc(root.get("updateTime")));
                return null;
            }
        };
    }

}
