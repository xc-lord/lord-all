package com.lord.common.model.sys;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * 站点sys_site的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 15:12:19
 */
@Entity
@Table(name = "sys_site")
public class SysSite implements Serializable {

	public static final String TABLE_NAME = "sys_site";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 站点名称
	 */
	@Column(name = "name", length = 100)
	private String name;

	/**
	 * 站点图片
	 */
	@Column(name = "site_img", nullable = true, length = 300)
	private String siteImg;

	/**
	 * 描述
	 */
	@Column(name = "intro", nullable = true, length = 200)
	private String intro;

	/**
	 * 拼音
	 */
	@Column(name = "letter", nullable = true, length = 50)
	private String letter;

	/**
	 * 域名
	 */
	@Column(name = "domain_name", nullable = true, length = 300)
	private String domainName;

	/**
	 * IP地址
	 */
	@Column(name = "ip_address", nullable = true, length = 100)
	private String ipAddress;

	/**
	 * 排序
	 */
	@Column(name = "order_value", nullable = true, length = 19)
	private Long orderValue;

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

	public String getSiteImg() {
		return this.siteImg;
	}

	public void setSiteImg(String siteImg) {
		this.siteImg = siteImg;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getLetter() {
		return this.letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getDomainName() {
		return this.domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Long getOrderValue() {
		return this.orderValue;
	}

	public void setOrderValue(Long orderValue) {
		this.orderValue = orderValue;
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

