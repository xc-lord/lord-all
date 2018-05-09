package com.lord.biz.dao.sys.specs;

import com.lord.biz.utils.BaseSpecification;
import com.lord.common.model.sys.SysExtendTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * 扩展属性模板sys_extend_template的动态SQL拼装工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月09日 17:34:08
 */
public class SysExtendTemplateSpecs extends BaseSpecification {

    /**
     * 根据查询条件，分页查询
     * @param pageObj   查询参数
     * @return
     */
    public static Specification<SysExtendTemplate> queryBySysExtendTemplate(final SysExtendTemplate pageObj) {
        return new Specification<SysExtendTemplate>() {
            public Predicate toPredicate(Root<SysExtendTemplate> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
