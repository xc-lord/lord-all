package com.lord.common.model.sys;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * 扩展属性sys_extend_attr的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月09日 18:08:35
 */
@Entity
@Table(name = "sys_extend_attr")
public class SysExtendAttr implements Serializable {

	public static final String TABLE_NAME = "sys_extend_attr";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 模板ID
	 */
	@Column(name = "template_id", nullable = true, length = 19)
	private Long templateId;

	/**
	 * 实体编码
	 */
	@Column(name = "entity_code", nullable = true, length = 50)
	private String entityCode;

	/**
	 * 属性名
	 */
	@Column(name = "name", nullable = true, length = 100)
	private String name;

	/**
	 * 属性key
	 */
	@Column(name = "data_key", nullable = true, length = 200)
	private String dataKey;

	/**
	 * 数据类型
	 */
	@Column(name = "data_type", nullable = true, length = 40)
	private String dataType;

	/**
	 * 属性类型
	 */
	@Column(name = "input_type", nullable = true, length = 40)
	private String inputType;

	/**
	 * 属性值的Json
	 */
	@Column(name = "val_json_str", nullable = true, length = 40)
	private String valJsonStr;

	/**
	 * 是否为空
	 */
	@Column(name = "nullable", nullable = true, length = 1)
	private Boolean nullable;

	/**
	 * 排序
	 */
	@Column(name = "order_value", nullable = true, length = 19)
	private Long orderValue;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getEntityCode() {
		return this.entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataKey() {
		return this.dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getInputType() {
		return this.inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getValJsonStr() {
		return this.valJsonStr;
	}

	public void setValJsonStr(String valJsonStr) {
		this.valJsonStr = valJsonStr;
	}

	public Boolean isNullable() {
		return this.nullable;
	}
	public Boolean getNullable() {
		return this.nullable;
	}

	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}

	public Long getOrderValue() {
		return this.orderValue;
	}

	public void setOrderValue(Long orderValue) {
		this.orderValue = orderValue;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

