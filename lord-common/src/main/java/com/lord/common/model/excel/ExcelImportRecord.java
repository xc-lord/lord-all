package com.lord.common.model.excel;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import com.lord.common.constant.excel.ExcelImportState;
import com.lord.common.constant.excel.ExcelImportWay;
import com.lord.common.model.mis.MisUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * Excel导入记录excel_import_record的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年03月26日 11:31:45
 */
@Entity
@Table(name = "excel_import_record")
public class ExcelImportRecord implements Serializable {

	public static final String TABLE_NAME = "excel_import_record";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Excel模板ID
	 */
	@Column(name = "template_id", nullable = true, length = 19)
	private Long templateId;

	/**
	 * 文件名
	 */
	@Column(name = "name", nullable = true, length = 300)
	private String name;

	/**
	 * 文件路径
	 */
	@Column(name = "file_path", nullable = true, length = 300)
	private String filePath;

	/**
	 * 文件大小
	 */
	@Column(name = "file_size", nullable = true, length = 19)
	private Long fileSize;

	/**
	 * 上传用户
	 */
	@ManyToOne
	@JoinColumn(name = "upload_user_id", foreignKey = @ForeignKey(name = "none"))
	private MisUser uploadUser;

	/**
	 * 导入用户
	 */
	@ManyToOne
	@JoinColumn(name = "import_user_id", foreignKey = @ForeignKey(name = "none"))
	private MisUser importUser;

	/**
	 * 导入次数
	 */
	@Column(name = "import_num", nullable = true, length = 10)
	private Integer importNum;

	/**
	 * 导入行数
	 */
	@Column(name = "line_num", nullable = true, length = 19)
	private Long lineNum;

	/**
	 * 导入状态
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "import_state", nullable = true, length = 40)
	private ExcelImportState importState;

	/**
	 * 导入方式
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "import_way", nullable = true, length = 40)
	private ExcelImportWay importWay;

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

	public Long getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public Long getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public MisUser getUploadUser()
	{
		return uploadUser;
	}

	public void setUploadUser(MisUser uploadUser)
	{
		this.uploadUser = uploadUser;
	}

	public MisUser getImportUser()
	{
		return importUser;
	}

	public void setImportUser(MisUser importUser)
	{
		this.importUser = importUser;
	}

	public Integer getImportNum() {
		return this.importNum;
	}

	public void setImportNum(Integer importNum) {
		this.importNum = importNum;
	}

	public Long getLineNum() {
		return this.lineNum;
	}

	public void setLineNum(Long lineNum) {
		this.lineNum = lineNum;
	}

	public ExcelImportState getImportState()
	{
		return importState;
	}

	public void setImportState(ExcelImportState importState)
	{
		this.importState = importState;
	}

	public ExcelImportWay getImportWay()
	{
		return importWay;
	}

	public void setImportWay(ExcelImportWay importWay)
	{
		this.importWay = importWay;
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

