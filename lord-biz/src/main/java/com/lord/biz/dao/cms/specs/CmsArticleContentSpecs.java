package com.lord.biz.dao.cms.specs;

import com.lord.biz.utils.BaseSpecification;
import com.lord.common.model.cms.CmsArticleContent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * 文章内容表cms_article_content的动态SQL拼装工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 18:06:04
 */
public class CmsArticleContentSpecs extends BaseSpecification {

    /**
     * 根据查询条件，分页查询
     * @param pageObj   查询参数
     * @return
     */
    public static Specification<CmsArticleContent> queryByCmsArticleContent(final CmsArticleContent pageObj) {
        return new Specification<CmsArticleContent>() {
            public Predicate toPredicate(Root<CmsArticleContent> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
