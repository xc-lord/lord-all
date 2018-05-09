package com.lord.common.model.sys;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * 扩展属性模板sys_extend_template的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月09日 17:34:08
 */
@Entity
@Table(name = "sys_extend_template")
public class SysExtendTemplate implements Serializable {

	public static final String TABLE_NAME = "sys_extend_template";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 模板名称
	 */
	@Column(name = "name", nullable = true, length = 50)
	private String name;

	/**
	 * 实体表名
	 */
	@Column(name = "entity_table", nullable = true, length = 50)
	private String entityTable;

	/**
	 * 实体编码
	 */
	@Column(name = "entity_code", nullable = true, length = 50)
	private String entityCode;

	/**
	 * 排序
	 */
	@Column(name = "order_value", nullable = true, length = 19)
	private Long orderValue;

	/**
	 * 创建人
	 */
	@Column(name = "creator", nullable = true, length = 19)
	private Long creator;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time", nullable = true, length = 19)
	private Date createTime;

	/**
	 * 更新人
	 */
	@Column(name = "modifier", nullable = true, length = 19)
	private Long modifier;

	/**
	 * 更新时间
	 */
	@Column(name = "update_time", nullable = true, length = 19)
	private Date updateTime;

	/**
	 * 是否删除
	 */
	@Column(name = "removed", nullable = true, length = 1)
	private Boolean removed;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEntityTable() {
		return this.entityTable;
	}

	public void setEntityTable(String entityTable) {
		this.entityTable = entityTable;
	}

	public String getEntityCode() {
		return this.entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Long getOrderValue() {
		return this.orderValue;
	}

	public void setOrderValue(Long orderValue) {
		this.orderValue = orderValue;
	}

	public Long getCreator() {
		return this.creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getModifier() {
		return this.modifier;
	}

	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean isRemoved() {
		return this.removed;
	}
	public Boolean getRemoved() {
		return this.removed;
	}

	public void setRemoved(Boolean removed) {
		this.removed = removed;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

