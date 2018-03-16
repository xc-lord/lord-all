package com.lord.common.model.excel;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * Excel模板配置excel_template的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月16日 15:51:05
 */
@Entity
@Table(name = "excel_template")
public class ExcelTemplate implements Serializable {

	public static final String TABLE_NAME = "excel_template";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Excel名称
	 */
	@Column(name = "excel_name", nullable = true, length = 200)
	private String excelName;

	/**
	 * 数据库表名
	 */
	@Column(name = "table_name", unique = true, nullable = true, length = 100)
	private String tableName;

	/**
	 * Excel起始行
	 */
	@Column(name = "excel_start_row", nullable = true, length = 10)
	private Integer excelStartRow;

	/**
	 * 导入方式
	 */
	@Column(name = "import_way", nullable = true, length = 50)
	private String importWay;

	/**
	 * 标识字段组
	 */
	@Column(name = "identify_column", nullable = true, length = 100)
	private String identifyColumn;

	/**
	 * Excel样例
	 */
	@Column(name = "excel_example", nullable = true, length = 300)
	private String excelExample;

	/**
	 * 分类Id
	 */
	@Column(name = "category_id", nullable = true, length = 19)
	private Long categoryId;

	/**
	 * 排序
	 */
	@Column(name = "order_value", nullable = true, length = 19)
	private Long orderValue;

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

	public String getExcelName() {
		return this.excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getExcelStartRow() {
		return this.excelStartRow;
	}

	public void setExcelStartRow(Integer excelStartRow) {
		this.excelStartRow = excelStartRow;
	}

	public String getImportWay() {
		return this.importWay;
	}

	public void setImportWay(String importWay) {
		this.importWay = importWay;
	}

	public String getIdentifyColumn() {
		return this.identifyColumn;
	}

	public void setIdentifyColumn(String identifyColumn) {
		this.identifyColumn = identifyColumn;
	}

	public String getExcelExample() {
		return this.excelExample;
	}

	public void setExcelExample(String excelExample) {
		this.excelExample = excelExample;
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
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

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

