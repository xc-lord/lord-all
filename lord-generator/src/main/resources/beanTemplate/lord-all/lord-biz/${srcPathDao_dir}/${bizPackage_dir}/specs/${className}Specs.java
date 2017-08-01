<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign hasColumn="com.lord.generator.ftl.FtlHasColumnMethod"?new()>
package ${basepackage}.biz.dao.${bizPackage}.specs;

import com.lord.biz.utils.BaseSpecification;
import ${basepackage}.common.model.${bizPackage}.${className};
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * ${bizName}${table.sqlName}的动态SQL拼装工具类
 *
 * @author xiaocheng
 * @version 1.0
 * @Date ${now?string('yyyy年MM月dd日 HH:mm:ss')}
 */
public class ${className}Specs extends BaseSpecification {

    /**
     * 根据查询条件，分页查询
     * @param pageObj   查询参数
     * @return
     */
    public static Specification<${className}> queryBy${className}(final ${className} pageObj) {
        return new Specification<${className}>() {
            public Predicate toPredicate(Root<${className}> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path id = root.get("id");
                <#if hasColumn(table.columns, "removed")>
                //存在逻辑删除判断
                Path removed = root.get("removed");
                Predicate predicate = builder.equal(removed, false);
                <#else>
                Predicate predicate = builder.conjunction();//添加where 1=1条件
                </#if>
                if (pageObj.getId() != null) {
                    //ID存在时，按ID进行查询
                    predicate = builder.and(predicate, builder.equal(id, pageObj.getId()));
                    return predicate;
                }
                //TODO:待修改
                <#if hasColumn(table.columns, "name")>
                if (StringUtils.isNotEmpty(pageObj.getName())) {
                    Path name = root.get("name");
                    predicate = builder.and(predicate, builder.like(name, "%" + pageObj.getName() + "%"));
                }
                </#if>
                query.where(predicate);
                <#if hasColumn(table.columns, "create_time")>
                <#if hasColumn(table.columns, "order_value")>
                query.orderBy(builder.desc(root.get("orderValue")), builder.desc(root.get("createTime")));
                <#else>
                query.orderBy(builder.desc(root.get("createTime")));
                </#if>
                </#if>
                return null;
            }
        };
    }

}
