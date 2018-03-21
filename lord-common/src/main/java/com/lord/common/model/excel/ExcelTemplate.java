package com.lord.common.model.excel;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import com.lord.common.model.mis.MisUser;
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
	 * 是否已经生成表
	 */
	@Column(name = "table_created", nullable = true)
	private Boolean tableCreated = false;

	/**
	 * 上次生成表的时间
	 */
	@Column(name = "table_created_time", nullable = true, length = 19)
	private Date tableCreatedTime;

	/**
	 * Excel起始行
	 */
	@Column(name = "excel_start_row", nullable = true, length = 10)
	private Integer excelStartRow;

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
	@ManyToOne
	@JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "none"))
	private ExcelCategory category;

	/**
	 * 支持全量导入
	 */
	@Column(name = "cover_all", nullable = true)
	private Boolean coverAll = false;

	/**
	 * 支持覆盖导入
	 */
	@Column(name = "cover_old", nullable = true)
	private Boolean coverOld = true;

	/**
	 * 支持追加导入
	 */
	@Column(name = "cover_append", nullable = true)
	private Boolean coverAppend = true;

	/**
	 * 排序
	 */
	@Column(name = "order_value", nullable = true, length = 19)
	private Long orderValue;

	/**
	 * 创建人
	 */
	@ManyToOne
	@JoinColumn(name = "creater", foreignKey = @ForeignKey(name = "none"))
	private MisUser creater;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time", nullable = true, length = 19)
	private Date createTime;

	/**
	 * 更新人
	 */
	@ManyToOne
	@JoinColumn(name = "modifier", foreignKey = @ForeignKey(name = "none"))
	private MisUser modifier;

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

	public Boolean getTableCreated()
	{
		return tableCreated;
	}

	public void setTableCreated(Boolean tableCreated)
	{
		this.tableCreated = tableCreated;
	}

	public Date getTableCreatedTime()
	{
		return tableCreatedTime;
	}

	public void setTableCreatedTime(Date tableCreatedTime)
	{
		this.tableCreatedTime = tableCreatedTime;
	}

	public Integer getExcelStartRow() {
		return this.excelStartRow;
	}

	public void setExcelStartRow(Integer excelStartRow) {
		this.excelStartRow = excelStartRow;
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

	public ExcelCategory getCategory()
	{
		return category;
	}

	public void setCategory(ExcelCategory category)
	{
		this.category = category;
	}

	public Boolean getCoverAll()
	{
		return coverAll;
	}

	public void setCoverAll(Boolean coverAll)
	{
		this.coverAll = coverAll;
	}

	public Boolean getCoverOld()
	{
		return coverOld;
	}

	public void setCoverOld(Boolean coverOld)
	{
		this.coverOld = coverOld;
	}

	public Boolean getCoverAppend()
	{
		return coverAppend;
	}

	public void setCoverAppend(Boolean coverAppend)
	{
		this.coverAppend = coverAppend;
	}

	public Long getOrderValue() {
		return this.orderValue;
	}

	public void setOrderValue(Long orderValue) {
		this.orderValue = orderValue;
	}

	public MisUser getCreater()
	{
		return creater;
	}

	public void setCreater(MisUser creater)
	{
		this.creater = creater;
	}

	public MisUser getModifier()
	{
		return modifier;
	}

	public void setModifier(MisUser modifier)
	{
		this.modifier = modifier;
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

