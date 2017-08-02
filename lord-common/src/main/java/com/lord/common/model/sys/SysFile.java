package com.lord.common.model.sys;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * 文件管理sys_file的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 11:39:47
 */
@Entity
@Table(name = "sys_file")
public class SysFile implements Serializable {

	public static final String TABLE_NAME = "sys_file";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 文件名称
	 */
	@Column(name = "name", nullable = true, length = 300)
	private String name;

	/**
	 * 文件描述
	 */
	@Column(name = "intro", nullable = true, length = 300)
	private String intro;

	/**
	 * 文件路径
	 */
	@Column(name = "file_path", length = 300)
	private String filePath;

	/**
	 * 文件大小
	 */
	@Column(name = "file_size", nullable = true, length = 19)
	private Long fileSize;

	/**
	 * 文件类型
	 */
	@Column(name = "file_type", nullable = true, length = 40)
	private String fileType;

	/**
	 * 文件后缀名
	 */
	@Column(name = "file_suffix", nullable = true, length = 40)
	private String fileSuffix;

	/**
	 * 文件别名
	 */
	@Column(name = "alias", nullable = true, length = 300)
	private String alias;

	/**
	 * 文件md5值
	 */
	@Column(name = "md_code", length = 100)
	private String mdCode;

	/**
	 * 目录id
	 */
	@Column(name = "directory_id", nullable = true, length = 19)
	private Long directoryId;

	/**
	 * 上传后台用户id
	 */
	@Column(name = "mis_user_id", nullable = true, length = 19)
	private Long misUserId;

	/**
	 * 上传用户id
	 */
	@Column(name = "user_id", nullable = true, length = 19)
	private Long userId;

	/**
	 * 上传时间
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Long getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileSuffix() {
		return this.fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getMdCode() {
		return this.mdCode;
	}

	public void setMdCode(String mdCode) {
		this.mdCode = mdCode;
	}

	public Long getDirectoryId() {
		return this.directoryId;
	}

	public void setDirectoryId(Long directoryId) {
		this.directoryId = directoryId;
	}

	public Long getMisUserId() {
		return this.misUserId;
	}

	public void setMisUserId(Long misUserId) {
		this.misUserId = misUserId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

