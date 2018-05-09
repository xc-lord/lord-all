<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign hasColumn="com.lord.generator.ftl.FtlHasColumnMethod"?new()>
package ${basepackage}.common.service.${bizPackage};

import ${basepackage}.common.dto.PagerSort;
import ${basepackage}.common.dto.Pager;
import ${basepackage}.common.dto.PagerParam;
import ${basepackage}.common.dto.user.LoginUser;
import ${basepackage}.common.model.${bizPackage}.${className};

/**
 * ${bizName}${table.sqlName}的Service
 *
 * @author xiaocheng
 * @version 1.0
 * @Date ${now?string('yyyy年MM月dd日 HH:mm:ss')}
 */
public interface ${className}Service {

    /**
     * 根据主键Id查询
     * @param id    主键Id
     * @return  数据库对象
     */
    ${className} get${className}(Long id);

<#if hasColumn(table.columns, "creator")>
    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @param loginUser 登录用户
     * @return  数据库对象
     */
    ${className} saveOrUpdate(${className} pageObj, LoginUser loginUser);
<#else>

    /**
     * 新增或者更新
     * @param pageObj   页面传参
     * @return  数据库对象
     */
    ${className} saveOrUpdate(${className} pageObj);
</#if>
    /**
     * 分页查询
     * @param param     查询参数
     * @param page      当前页数
     * @param pageSize  分页大小
     * @return  分页结果
     */
    Pager<${className}> page${className}(${className} param, int page, int pageSize);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @return  分页结果
     */
    Pager<${className}> page${className}(${className} param, PagerParam pagerParam);

    /**
     * 分页查询
     * @param param         查询参数
     * @param pagerParam    分页参数
     * @param sorts         排序字段
     * @return  分页结果
     */
    Pager<${className}> page${className}(${className} param, PagerParam pagerParam, PagerSort... sorts);

    /**
     * 物理删除
     * @param ids    主键ID
     */
    void delete${className}(Long... ids);

    <#-- 根据表的字段需要生成不同的方法 -->
    <#list table.columns as column>
    <#if column.columnNameLower == 'removed'>
    /**
     * 逻辑删除
     * @param ids    主键ID
     */
    void remove${className}(Long... ids);
    <#elseif column.columnNameLower == 'orderValue'>

    /**
     * 更新排序值
     * @param id            主键Id
     * @param orderValue    排序值
     */
    void updateOrderValue(Long id, Long orderValue);
    </#if>
    </#list>

    /**
     * 是否存在相同的记录
     * @param id        主键Id
     * @param rowName   属性名
     * @param rowValue  属性值
     * @return
     */
    boolean isExist(Long id, String rowName, String rowValue);
}