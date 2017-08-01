<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.biz.dao.${bizPackage};

import ${basepackage}.common.model.${bizPackage}.${className};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${bizName}${table.sqlName}的Dao
 *
 * @author xiaocheng
 * @version 1.0
 * @Date ${now?string('yyyy年MM月dd日 HH:mm:ss')}
 */
@Repository
public interface ${className}Dao extends JpaRepository<${className}, Long>, JpaSpecificationExecutor<${className}> {

    @Modifying
    @Query("delete from ${className} where id in ?1")
    void delete${className}(Long... ids);

    @Query("select u from ${className} u where u.id in ?1")
    List<${className}> findByIds(Long... ids);

    <#-- 根据表的字段需要生成不同的方法 -->
    <#list table.columns as column>
    <#if column.columnNameLower == 'removed'>
    @Modifying
    @Query("update ${className} u set u.removed = true where u.id in ?1")
    void remove${className}(Long... id);
    <#elseif column.columnNameLower == 'orderValue'>

    @Modifying
    @Query("update ${className} u set u.orderValue = ?2 where u.id = ?1")
    void updateOrderValue(Long id, Long orderValue);
    </#if>
    </#list>

	//在此添加你的自定义方法...
}