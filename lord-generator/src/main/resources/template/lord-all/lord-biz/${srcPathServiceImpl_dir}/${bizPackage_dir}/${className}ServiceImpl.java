<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign hasColumn="com.lord.generator.ftl.FtlHasColumnMethod"?new()>
package ${basepackage}.biz.service.${bizPackage};

import ${basepackage}.biz.dao.${bizPackage}.${className}Dao;
import ${basepackage}.biz.dao.${bizPackage}.specs.${className}Specs;
import ${basepackage}.biz.utils.ServiceUtils;
import ${basepackage}.common.dto.Pager;
import ${basepackage}.common.dto.PagerParam;
import ${basepackage}.common.dto.PagerSort;
import ${basepackage}.common.model.${bizPackage}.${className};
import ${basepackage}.common.service.${bizPackage}.${className}Service;
import ${basepackage}.utils.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ${bizName}${table.sqlName}的Service实现
 *
 * @author xiaocheng
 * @version 1.0
 * @Date ${now?string('yyyy年MM月dd日 HH:mm:ss')}
 */
@Component
public class ${className}ServiceImpl implements ${className}Service {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ${className}Dao ${classNameLower}Dao;

    @Override
    public ${className} get${className}(Long id) {
        return ${classNameLower}Dao.findOne(id);
    }

    @Override
    @Transactional
    public ${className} saveOrUpdate(${className} pageObj) {
        Preconditions.checkNotNull(pageObj, "保存对象不能为空");

        if(logger.isDebugEnabled())
            logger.debug("保存" + pageObj);

        //验证字段的唯一性
        <#if hasColumn(table.columns, "name")>
        Preconditions.checkArgument(isExist(pageObj.getId(), "name", pageObj.getName()), "名称[" + pageObj.getName() + "]已经存在");
        </#if>

        //新增记录
        if (pageObj.getId() == null) {
            //设置默认属性
            <#-- 根据表的字段需要生成不同的方法 -->
            <#list table.columns as column>
            <#if column.columnNameLower == 'removed'>
            pageObj.setRemoved(false);
            <#elseif column.columnNameLower == 'createTime'>
            pageObj.setCreateTime(new Date());
            <#elseif column.columnNameLower == 'updateTime'>
            pageObj.setUpdateTime(new Date());
            </#if>
            </#list>

            ${classNameLower}Dao.save(pageObj);//新增
            return pageObj;
        }

        //更新记录
        ${className} dbObj = ${classNameLower}Dao.findOne(pageObj.getId());
        Preconditions.checkNotNull(dbObj, "更新的记录不存在");
        //不能修改的字段
        <#-- 根据表的字段需要生成不同的方法 -->
        <#list table.columns as column>
        <#if column.columnNameLower == 'removed'>
        pageObj.setRemoved(dbObj.isRemoved());
        <#elseif column.columnNameLower == 'updateTime'>
        pageObj.setUpdateTime(new Date());//更新时间
        <#elseif column.columnNameLower == 'createTime'>
        pageObj.setCreateTime(dbObj.getCreateTime());
        </#if>
        </#list>

        ${classNameLower}Dao.save(pageObj);//更新

        return pageObj;
    }

    @Override
    public Pager<${className}> page${className}(${className} param, int page, int pageSize) {
        PagerParam pagerParam = new PagerParam(page, pageSize);
        return page${className}(param, pagerParam);

    }

    @Override
    public Pager<${className}> page${className}(${className} param, PagerParam pagerParam) {
        PageRequest pageRequest = new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize());
        Page<${className}> pageResult = ${classNameLower}Dao.findAll(${className}Specs.queryBy${className}(param), pageRequest);
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    public Pager<${className}> page${className}(${className} param, PagerParam pagerParam, PagerSort... sorts) {
        Sort sort = ServiceUtils.parseSort(sorts);
        Page<${className}> pageResult = null;
        //ID存在时，按ID进行查询
        if (param != null && param.getId() != null) {
            ${className} obj = ${classNameLower}Dao.findOne(param.getId());
            return ServiceUtils.toPager(obj, pagerParam);
        }
        if(sort != null) {
            pageResult = ${classNameLower}Dao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize(), sort));
        } else {
            pageResult = ${classNameLower}Dao.findAll(new PageRequest(pagerParam.getPage() - 1, pagerParam.getPageSize()));
        }
        return ServiceUtils.toPager(pageResult, pagerParam);
    }

    @Override
    @Transactional
    public void delete${className}(Long... ids) {
        ${classNameLower}Dao.delete${className}(ids);
    }

    <#-- 根据表的字段需要生成不同的方法 -->
    <#list table.columns as column>
    <#if column.columnNameLower == 'removed'>

    @Override
    @Transactional
    public void remove${className}(Long... ids) {
        ${classNameLower}Dao.remove${className}(ids);
    }
    <#elseif column.columnNameLower == 'orderValue'>

    @Override
    @Transactional
    public void updateOrderValue(Long id, Long orderValue) {
        ${className} dbObj = ${classNameLower}Dao.findOne(id);
        Preconditions.checkNotNull(dbObj, "更新排序的记录不存在");
        if (orderValue.equals(dbObj.getOrderValue())) {
            return;
        }
        ${classNameLower}Dao.updateOrderValue(id, orderValue);
    }
    </#if>
    </#list>

    @Override
    public boolean isExist(Long id, String rowName, String rowValue) {
        List<String> rowList = new ArrayList<>();
        rowList.add("name");
        rowList.add("username");
        Preconditions.checkArgument(!rowList.contains(rowName), "此字段不需要判断是否存在");
        <#if hasColumn(table.columns, "removed")>
        List<${className}> list = ${classNameLower}Dao.findAll(${className}Specs.queryBy(rowName, rowValue, false, ${className}.class));
        <#else>
        List<${className}> list = ${classNameLower}Dao.findAll(${className}Specs.queryBy(rowName, rowValue, ${className}.class));
        </#if>
        if (list == null || list.size() < 1) {
            return false;
        }
        ${className} dbObj = list.get(0);
        //编辑时，此字段跟数据库记录一致，不认为重复
        if (dbObj.getId().equals(id)) {
            return false;
        }
        return true;
    }
}