package com.lord.biz.dao.cms.specs;

import com.lord.biz.utils.BaseSpecification;
import com.lord.common.model.cms.CmsTags;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * 文章标签cms_tags的动态SQL拼装工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 15:42:51
 */
public class CmsTagsSpecs extends BaseSpecification {

    /**
     * 根据查询条件，分页查询
     * @param pageObj   查询参数
     * @return
     */
    public static Specification<CmsTags> queryByCmsTags(final CmsTags pageObj) {
        return new Specification<CmsTags>() {
            public Predicate toPredicate(Root<CmsTags> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path id = root.get("id");
                //存在逻辑删除判断
                Path removed = root.get("removed");
                Predicate predicate = builder.equal(removed, false);
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
                query.orderBy(builder.desc(root.get("orderValue")), builder.desc(root.get("createTime")));
                return null;
            }
        };
    }

}
