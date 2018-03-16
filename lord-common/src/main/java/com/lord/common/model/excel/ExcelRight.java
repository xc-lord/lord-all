package com.lord.common.model.excel;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * Excel权限配置excel_right的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月16日 16:24:05
 */
@Entity
@Table(name = "excel_right")
public class ExcelRight implements Serializable {

	public static final String TABLE_NAME = "excel_right";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Excel模板ID
	 */
	@Column(name = "excel_template_id", nullable = true, length = 19)
	private Long excelTemplateId;

	/**
	 * 角色ID
	 */
	@Column(name = "role_id", nullable = true, length = 19)
	private Long roleId;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getExcelTemplateId() {
		return this.excelTemplateId;
	}

	public void setExcelTemplateId(Long excelTemplateId) {
		this.excelTemplateId = excelTemplateId;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

