<#assign hasColumn="com.lord.generator.ftl.FtlHasColumnMethod"?new()>

<#assign x = "something">
<#if hasColumn(table.columns, "order_value")>
    表中有orderValue这个字段
<#else>
    表中没有orderValue这个字段啊啊啊啊啊
</#if>
