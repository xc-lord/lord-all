package com.lord.common.model.sys;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * 实体的扩展属性值sys_extend_attribute的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月22日 16:53:03
 */
@Entity
@Table(name = "sys_extend_attribute")
public class SysExtendAttribute implements Serializable {

	public static final String TABLE_NAME = "sys_extend_attribute";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 实体ID
	 */
	@Column(name = "entity_id", nullable = true, length = 19)
	private Long entityId;

	/**
	 * 实体编码
	 */
	@Column(name = "entity_code", nullable = true, length = 50)
	private String entityCode;

	/**
	 * 属性ID
	 */
	@Column(name = "attr_id", nullable = true, length = 19)
	private Long attrId;

	/**
	 * 属性名称
	 */
	@Column(name = "attr_name", nullable = true, length = 100)
	private String attrName;

	/**
	 * 属性key
	 */
	@Column(name = "attr_key", nullable = true, length = 100)
	private String attrKey;

	/**
	 * 属性类型
	 */
	@Column(name = "data_type", nullable = true, length = 40)
	private String dataType;

	/**
	 * 属性值
	 */
	@Column(name = "attr_value", nullable = true, length = 100)
	private String attrValue;

	/**
	 * 时间值
	 */
	@Column(name = "attr_value_time", nullable = true, length = 19)
	private Date attrValueTime;

	/**
	 * 数字值
	 */
	@Column(name = "attr_value_num", nullable = true, length = 22)
	private Double attrValueNum;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEntityId() {
		return this.entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getEntityCode() {
		return this.entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Long getAttrId() {
		return this.attrId;
	}

	public void setAttrId(Long attrId) {
		this.attrId = attrId;
	}

	public String getAttrName() {
		return this.attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrKey()
	{
		return attrKey;
	}

	public void setAttrKey(String attrKey)
	{
		this.attrKey = attrKey;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getAttrValue() {
		return this.attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public Date getAttrValueTime() {
		return this.attrValueTime;
	}

	public void setAttrValueTime(Date attrValueTime) {
		this.attrValueTime = attrValueTime;
	}

	public Double getAttrValueNum() {
		return this.attrValueNum;
	}

	public void setAttrValueNum(Double attrValueNum) {
		this.attrValueNum = attrValueNum;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

