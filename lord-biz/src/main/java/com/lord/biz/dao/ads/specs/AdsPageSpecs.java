package com.lord.biz.dao.ads.specs;

import com.lord.biz.utils.BaseSpecification;
import com.lord.common.dto.ads.AdsPageQuery;
import com.lord.common.model.ads.AdsPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * 页面ads_page的动态SQL拼装工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 09:13:24
 */
public class AdsPageSpecs extends BaseSpecification {

    /**
     * 根据查询条件，分页查询
     * @param pageObj   查询参数
     * @return
     */
    public static Specification<AdsPage> queryByAdsPage(final AdsPage pageObj) {
        return new Specification<AdsPage>() {
            public Predicate toPredicate(Root<AdsPage> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
                query.orderBy(builder.desc(root.get("orderValue")), builder.desc(root.get("createTime")));
                return null;
            }
        };
    }

    public static Specification<AdsPage> queryByAdsPage(final AdsPageQuery pageObj)
    {
        return new Specification<AdsPage>() {
            public Predicate toPredicate(Root<AdsPage> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path id = root.get("id");
                Predicate predicate = builder.conjunction();//添加where 1=1条件
                if (pageObj.getId() != null) {
                    //ID存在时，按ID进行查询
                    predicate = builder.and(predicate, builder.equal(id, pageObj.getId()));
                    return predicate;
                }
                if (StringUtils.isNotEmpty(pageObj.getPageCode()))
                {
                    predicate = builder.and(predicate, builder.equal(root.get("pageCode"), pageObj.getPageCode()));
                }
                if (pageObj.getAppType() != null)
                {
                    predicate = builder.and(predicate, builder.equal(root.get("appType"), pageObj.getAppType().toString()));
                }
                if (pageObj.getPageType() != null)
                {
                    predicate = builder.and(predicate, builder.equal(root.get("pageType"), pageObj.getPageType().toString()));
                }
                if (pageObj.getPageState() != null)
                {
                    predicate = builder.and(predicate, builder.equal(root.get("pageState"), pageObj.getPageState().toString()));
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
