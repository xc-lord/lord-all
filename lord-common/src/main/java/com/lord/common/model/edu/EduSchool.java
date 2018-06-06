package com.lord.common.model.edu;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * 学校edu_school的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月06日 10:15:50
 */
@Entity
@Table(name = "edu_school")
public class EduSchool implements Serializable {

	public static final String TABLE_NAME = "edu_school";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 名称
	 */
	@Column(name = "name", nullable = true, length = 100)
	private String name;

	/**
	 * 英文名
	 */
	@Column(name = "en_name", nullable = true, length = 100)
	private String enName;

	/**
	 * 学校Logo
	 */
	@Column(name = "logo_img", nullable = true, length = 300)
	private String logoImg;

	/**
	 * 介绍
	 */
	@Column(name = "intro", nullable = true, length = 500)
	private String intro;

	/**
	 * 省ID
	 */
	@Column(name = "province_id", nullable = true, length = 19)
	private Long provinceId;

	/**
	 * 市ID
	 */
	@Column(name = "city_id", nullable = true, length = 19)
	private Long cityId;

	/**
	 * 县ID
	 */
	@Column(name = "county_id", nullable = true, length = 19)
	private Long countyId;

	/**
	 * 镇ID
	 */
	@Column(name = "town_id", nullable = true, length = 19)
	private Long townId;

	/**
	 * 详细地址
	 */
	@Column(name = "address", nullable = true, length = 300)
	private String address;

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

	public String getEnName() {
		return this.enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getLogoImg() {
		return this.logoImg;
	}

	public void setLogoImg(String logoImg) {
		this.logoImg = logoImg;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Long getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return this.cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getCountyId() {
		return this.countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}

	public Long getTownId() {
		return this.townId;
	}

	public void setTownId(Long townId) {
		this.townId = townId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

