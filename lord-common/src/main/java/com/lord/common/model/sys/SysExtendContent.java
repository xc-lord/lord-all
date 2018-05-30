package com.lord.common.model.sys;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * 扩展内容sys_extend_content的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年05月29日 20:18:20
 */
@Entity
@Table(name = "sys_extend_content")
public class SysExtendContent implements Serializable {

	public static final String TABLE_NAME = "sys_extend_content";

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
	 * 内容
	 */
	@Lob
	@Column(name = "content", nullable = true, length = 65535)
	private String content;

	/**
	 * 编辑的内容
	 */
	@Lob
	@Column(name = "content_edit", nullable = true, length = 65535)
	private String contentEdit;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time", nullable = true, length = 19)
	private Date createTime;

	/**
	 * 更新时间
	 */
	@Column(name = "update_time", nullable = true, length = 19)
	private Date updateTime;

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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentEdit() {
		return this.contentEdit;
	}

	public void setContentEdit(String contentEdit) {
		this.contentEdit = contentEdit;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

