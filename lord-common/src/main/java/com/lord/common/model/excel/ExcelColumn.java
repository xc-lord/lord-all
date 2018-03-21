package com.lord.common.model.excel;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * Excel列映射关系excel_column的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月20日 11:39:54
 */
@Entity
@Table(name = "excel_column")
public class ExcelColumn implements Serializable {

	public static final String TABLE_NAME = "excel_column";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Excel模板ID
	 */
	@Column(name = "excel_template_id", nullable = true, length = 19)
	private Long excelTemplateId;

	/**
	 * Excel列名
	 */
	@Column(name = "excel_column", nullable = true, length = 100)
	private String excelColumn;

	/**
	 * 数据库列名
	 */
	@Column(name = "db_column", nullable = true, length = 200)
	private String dbColumn;

	/**
	 * 数据类型
	 */
	@Column(name = "column_type", nullable = true, length = 40)
	private String columnType;

	/**
	 * 长度
	 */
	@Column(name = "column_length", nullable = true, length = 10)
	private Integer columnLength;

	/**
	 * 是否为空
	 */
	@Column(name = "nullable", nullable = true, length = 1)
	private Boolean nullable;

	/**
	 * 是否标识
	 */
	@Column(name = "identification", nullable = true, length = 1)
	private Boolean identification;

	/**
	 * 开始时间
	 */
	@Column(name = "start_time", nullable = true, length = 19)
	private Date startTime;

	/**
	 * 结束时间
	 */
	@Column(name = "end_time", nullable = true, length = 19)
	private Date endTime;

	/**
	 * 最小值
	 */
	@Column(name = "min_value", nullable = true, length = 22)
	private Double minValue;

	/**
	 * 最大值
	 */
	@Column(name = "max_value", nullable = true, length = 22)
	private Double maxValue;

	/**
	 * 排序
	 */
	@Column(name = "order_value", nullable = true, length = 19)
	private Long orderValue;

	/**
	 * 时间格式
	 */
	@Column(name = "time_format", nullable = true, length = 50)
	private String timeFormat;

	/**
	 * 创建人
	 */
	@Column(name = "creater", nullable = true, length = 19)
	private Long creater;

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

	public String getExcelColumn() {
		return this.excelColumn;
	}

	public void setExcelColumn(String excelColumn) {
		this.excelColumn = excelColumn;
	}

	public String getDbColumn() {
		return this.dbColumn;
	}

	public void setDbColumn(String dbColumn) {
		this.dbColumn = dbColumn;
	}

	public String getColumnType() {
		return this.columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public Integer getColumnLength() {
		return this.columnLength;
	}

	public void setColumnLength(Integer columnLength) {
		this.columnLength = columnLength;
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

	public Boolean isIdentification() {
		return this.identification;
	}
	public Boolean getIdentification() {
		return this.identification;
	}

	public void setIdentification(Boolean identification) {
		this.identification = identification;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Double getMinValue() {
		return this.minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return this.maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	public Long getOrderValue() {
		return this.orderValue;
	}

	public void setOrderValue(Long orderValue) {
		this.orderValue = orderValue;
	}

	public Long getCreater() {
		return this.creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
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

	public String getTimeFormat()
	{
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat)
	{
		this.timeFormat = timeFormat;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

