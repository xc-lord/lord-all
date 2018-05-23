<#assign hasColumn="com.lord.generator.ftl.FtlHasColumnMethod"?new()>

<#assign x = "something">
<#if hasColumn(table.columns, "order_value")>
    表中有orderValue这个字段
<#else>
    表中没有orderValue这个字段啊啊啊啊啊
</#if>


<#list table.columns as column>
    <#if column.sqlName?contains("img") || column.sqlName?contains("icon")>
        ${column.sqlName}
    </#if>
</#list>