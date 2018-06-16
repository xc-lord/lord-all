package com.lord.biz.dao.cms.specs;

import com.lord.biz.utils.BaseSpecification;
import com.lord.common.model.cms.CmsArticle;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * 文章cms_article的动态SQL拼装工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 18:09:10
 */
public class CmsArticleSpecs extends BaseSpecification {

    /**
     * 根据查询条件，分页查询
     * @param pageObj   查询参数
     * @return
     */
    public static Specification<CmsArticle> queryByCmsArticle(final CmsArticle pageObj) {
        return new Specification<CmsArticle>() {
            public Predicate toPredicate(Root<CmsArticle> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path id = root.get("id");
                //存在逻辑删除判断
                Path removed = root.get("removed");
                Predicate predicate = builder.equal(removed, false);
                if (pageObj.getId() != null) {
                    //ID存在时，按ID进行查询
                    predicate = builder.and(predicate, builder.equal(id, pageObj.getId()));
                    return predicate;
                }
                if (pageObj.getCatId() != null)
                {
                    Path catId = root.get("catId");
                    predicate = builder.and(predicate, builder.equal(catId, pageObj.getCatId()));
                }
                if (pageObj.getCatOneId() != null)
                {
                    Path catOneId = root.get("catOneId");
                    predicate = builder.and(predicate, builder.equal(catOneId, pageObj.getCatOneId()));
                }
                if (pageObj.getCatTwoId() != null)
                {
                    Path catTwoId = root.get("catTwoId");
                    predicate = builder.and(predicate, builder.equal(catTwoId, pageObj.getCatTwoId()));
                }
                if (pageObj.getCatThreeId() != null)
                {
                    Path catThreeId = root.get("catThreeId");
                    predicate = builder.and(predicate, builder.equal(catThreeId, pageObj.getCatThreeId()));
                }
                query.where(predicate);
                query.orderBy(builder.desc(root.get("orderValue")), builder.desc(root.get("createTime")));
                return null;
            }
        };
    }

    public static Specification<CmsArticle> queryAll() {
        return new Specification<CmsArticle>() {
            public Predicate toPredicate(Root<CmsArticle> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path id = root.get("id");
                //存在逻辑删除判断
                Path removed = root.get("removed");
                Predicate predicate = builder.equal(removed, false);
                query.where(predicate);
                query.orderBy(builder.desc(root.get("orderValue")), builder.desc(root.get("createTime")));
                return null;
            }
        };
    }
}
