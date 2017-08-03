package com.lord.common.model.ads;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import com.lord.common.dto.cat.CategorySimple;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * 广告位ads_space的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 11:12:10
 */
@Entity
@Table(name = "ads_space")
public class AdsSpace implements Serializable,CategorySimple {

	public static final String TABLE_NAME = "ads_space";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 名称
	 */
	@Column(name = "name", nullable = true, length = 100)
	private String name;

	/**
	 * 关键字
	 */
	@Column(name = "keyword", unique = true, length = 200)
	private String keyword;

	/**
	 * 子关键字
	 */
	@Column(name = "sub_keyword", length = 100)
	private String subKeyword;

	/**
	 * 图片、文字、商品、文章
	 */
	@Column(name = "ads_type", length = 100)
	private String adsType;

	/**
	 * 元素的数量，0不限制数量
	 */
	@Column(name = "ads_num", nullable = true, length = 10)
	private Integer adsNum;

	/**
	 * 显示1、隐藏0
	 */
	@Column(name = "show_state", nullable = true, length = 1)
	private Boolean showState;

	/**
	 * 图片
	 */
	@Column(name = "space_img", nullable = true, length = 300)
	private String spaceImg;

	/**
	 * 链接
	 */
	@Column(name = "space_url", nullable = true, length = 300)
	private String spaceUrl;

	/**
	 * 等级
	 */
	@Column(name = "level", nullable = true, length = 10)
	private Integer level;

	/**
	 * 父节点Id
	 */
	@Column(name = "parent_id", nullable = true, length = 19)
	private Long parentId;

	/**
	 * 页面id
	 */
	@Column(name = "page_id", length = 19)
	private Long pageId;

	/**
	 * 备注
	 */
	@Column(name = "remark", nullable = true, length = 200)
	private String remark;

	/**
	 * 介绍
	 */
	@Column(name = "intro", nullable = true, length = 200)
	private String intro;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time", nullable = true, length = 19)
	private Date createTime;

	/**
	 * 最后修改时间
	 */
	@Column(name = "update_time", nullable = true, length = 19)
	private Date updateTime;

	/**
	 * 排序
	 */
	@Column(name = "order_value", nullable = true, length = 19)
	private Long orderValue;

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

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSubKeyword() {
		return this.subKeyword;
	}

	public void setSubKeyword(String subKeyword) {
		this.subKeyword = subKeyword;
	}

	public String getAdsType() {
		return this.adsType;
	}

	public void setAdsType(String adsType) {
		this.adsType = adsType;
	}

	public Integer getAdsNum() {
		return this.adsNum;
	}

	public void setAdsNum(Integer adsNum) {
		this.adsNum = adsNum;
	}

	public Boolean isShowState() {
		return this.showState;
	}
	public Boolean getShowState() {
		return this.showState;
	}

	public void setShowState(Boolean showState) {
		this.showState = showState;
	}

	public String getSpaceImg() {
		return this.spaceImg;
	}

	public void setSpaceImg(String spaceImg) {
		this.spaceImg = spaceImg;
	}

	public String getSpaceUrl() {
		return this.spaceUrl;
	}

	public void setSpaceUrl(String spaceUrl) {
		this.spaceUrl = spaceUrl;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getPageId() {
		return this.pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
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

