<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.common.model.${bizPackage};

<#include "/java_bean_imports.include">

/**
 * ${bizName}${table.sqlName}的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date ${now?string('yyyy年MM月dd日 HH:mm:ss')}
 */
@Entity
@Table(name = "${table.sqlName}")
public class ${className} implements Serializable {

	public static final String TABLE_NAME = "${table.sqlName}";
	<@generateFields/>
	<@generateSeterAndGeter/>
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

<#macro generateFields>
	<#list table.columns as column>
	<#if column.pk>

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private ${column.javaType?substring(column.javaType?last_index_of('.') + 1)} ${column.columnNameLower};
	<#else>

	/**
	 * ${column.remarks}
	 */
	<#if column.size gt 2000>
	@Lob
	</#if>
	<#if column.fk>
	//外键
	</#if>
	@Column(name = "${column.sqlName}",${column.unique?string(' unique = true,','')}${column.nullable?string(' nullable = true,','')} length = ${column.size})
	private ${column.javaType?substring(column.javaType?last_index_of('.') + 1)} ${column.columnNameLower};
	</#if>
	</#list>

</#macro>

<#macro generateSeterAndGeter>
	<#list table.columns as column>
	<#if column.javaType == 'java.lang.Boolean'>
	public ${column.javaType?substring(column.javaType?last_index_of('.') + 1)} is${column.columnName}() {
		return this.${column.columnNameLower};
	}
	</#if>
	public ${column.javaType?substring(column.javaType?last_index_of('.') + 1)} get${column.columnName}() {
		return this.${column.columnNameLower};
	}

	public void set${column.columnName}(${column.javaType?substring(column.javaType?last_index_of('.') + 1)} ${column.columnNameLower}) {
		this.${column.columnNameLower} = ${column.columnNameLower};
	}

	</#list>
</#macro>

