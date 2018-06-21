package com.lord.biz.dao.common.specs;

import com.lord.biz.utils.BaseSpecification;
import com.lord.common.model.common.CommonQuestion;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * 常见问题common_question的动态SQL拼装工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月16日 18:06:42
 */
public class CommonQuestionSpecs extends BaseSpecification {

    /**
     * 根据查询条件，分页查询
     * @param pageObj   查询参数
     * @return
     */
    public static Specification<CommonQuestion> queryByCommonQuestion(final CommonQuestion pageObj) {
        return new Specification<CommonQuestion>() {
            public Predicate toPredicate(Root<CommonQuestion> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path id = root.get("id");
                Predicate predicate = builder.conjunction();//添加where 1=1条件
                if (pageObj.getId() != null) {
                    //ID存在时，按ID进行查询
                    predicate = builder.and(predicate, builder.equal(id, pageObj.getId()));
                    return predicate;
                }
                if (StringUtils.isNotEmpty(pageObj.getEntityCode())) {
                    Path entityCode = root.get("entityCode");
                    predicate = builder.and(predicate, builder.equal(entityCode, pageObj.getEntityCode()));
                }
                if (pageObj.getEntityId() != null) {
                    Path entityId = root.get("entityId");
                    predicate = builder.and(predicate, builder.equal(entityId, pageObj.getEntityId()));
                }
                query.where(predicate);
                query.orderBy(builder.desc(root.get("orderValue")), builder.desc(root.get("createTime")));
                return null;
            }
        };
    }

}
