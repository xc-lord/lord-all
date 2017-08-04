package com.lord.biz.dao.ads.specs;

import com.lord.biz.utils.BaseSpecification;
import com.lord.common.model.ads.AdsElement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * 广告位的元素ads_element的动态SQL拼装工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 16:18:22
 */
public class AdsElementSpecs extends BaseSpecification {

    /**
     * 根据查询条件，分页查询
     * @param pageObj   查询参数
     * @return
     */
    public static Specification<AdsElement> queryByAdsElement(final AdsElement pageObj) {
        return new Specification<AdsElement>() {
            public Predicate toPredicate(Root<AdsElement> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path id = root.get("id");
                Predicate predicate = builder.conjunction();//添加where 1=1条件
                if (pageObj.getId() != null) {
                    //ID存在时，按ID进行查询
                    predicate = builder.and(predicate, builder.equal(id, pageObj.getId()));
                    return predicate;
                }
                if (pageObj.getSpaceId() != null)
                {
                    predicate = builder.and(predicate, builder.equal(root.get("spaceId"), pageObj.getSpaceId()));
                }
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
