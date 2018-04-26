package com.lord.common.model.sys;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * 行政区域sys_district的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年04月24日 15:04:09
 */
@Entity
@Table(name = "sys_district")
public class SysDistrict implements Serializable {

	public static final String TABLE_NAME = "sys_district";

	@Id
	private Long id;

	/**
	 * 城市编码
	 */
	@Column(name = "citycode", nullable = true, length = 40)
	private String citycode;

	/**
	 * 区域编码
	 */
	@Column(name = "adcode", nullable = true, length = 40)
	private String adcode;

	/**
	 * 行政区名称
	 */
	@Column(name = "name", nullable = true, length = 40)
	private String name;

	/**
	 * 经度
	 */
	@Column(name = "longitude", nullable = true, length = 22)
	private Double longitude;

	/**
	 * 纬度
	 */
	@Column(name = "latitude", nullable = true, length = 22)
	private Double latitude;

	/**
	 * 行政区划级别
	 */
	@Column(name = "level", nullable = true, length = 40)
	private String level;

	/**
	 * 邮编
	 */
	@Column(name = "zip_code", nullable = true, length = 20)
	private String zipCode;

	/**
	 * 父区域ID
	 */
	@Column(name = "parent_id", nullable = true, length = 19)
	private Long parentId;

	/**
	 * 更新时间
	 */
	@Column(name = "update_time", nullable = true, length = 19)
	private Date updateTime;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time", nullable = true, length = 19)
	private Date createTime;

	/**
	 * 排序值
	 */
	@Column(name = "order_value", nullable = true, length = 19)
	private Long orderValue;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCitycode() {
		return this.citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getAdcode() {
		return this.adcode;
	}

	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getOrderValue() {
		return this.orderValue;
	}

	public void setOrderValue(Long orderValue) {
		this.orderValue = orderValue;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

