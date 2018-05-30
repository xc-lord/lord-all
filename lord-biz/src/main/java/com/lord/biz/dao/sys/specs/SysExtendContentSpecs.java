package com.lord.biz.dao.sys.specs;

import com.lord.biz.utils.BaseSpecification;
import com.lord.common.model.sys.SysExtendContent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * 扩展内容sys_extend_content的动态SQL拼装工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月29日 20:18:20
 */
public class SysExtendContentSpecs extends BaseSpecification {

    /**
     * 根据查询条件，分页查询
     * @param pageObj   查询参数
     * @return
     */
    public static Specification<SysExtendContent> queryBySysExtendContent(final SysExtendContent pageObj) {
        return new Specification<SysExtendContent>() {
            public Predicate toPredicate(Root<SysExtendContent> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path id = root.get("id");
                Predicate predicate = builder.conjunction();//添加where 1=1条件
                if (pageObj.getId() != null) {
                    //ID存在时，按ID进行查询
                    predicate = builder.and(predicate, builder.equal(id, pageObj.getId()));
                    return predicate;
                }
                //TODO:待修改
                query.where(predicate);
                query.orderBy(builder.desc(root.get("createTime")));
                return null;
            }
        };
    }

}
