package com.lord.common.model.ads;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * 页面ads_page的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 09:13:24
 */
@Entity
@Table(name = "ads_page")
public class AdsPage implements Serializable {

	public static final String TABLE_NAME = "ads_page";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 页面名称
	 */
	@Column(name = "name", length = 100)
	private String name;

	/**
	 * 页面代码
	 */
	@Column(name = "page_code", unique = true, length = 100)
	private String pageCode;

	/**
	 * 封面图片
	 */
	@Column(name = "page_img", nullable = true, length = 300)
	private String pageImg;

	/**
	 * 平台类型(PC、WX、APP)
	 */
	@Column(name = "app_type", nullable = true, length = 255)
	private String appType;

	/**
	 * 页面类型：常规、专题
	 */
	@Column(name = "page_type", length = 100)
	private String pageType;

	/**
	 * 页面状态
	 */
	@Column(name = "page_state", length = 100)
	private String pageState;

	/**
	 * SEO描述
	 */
	@Column(name = "seo_desc", nullable = true, length = 300)
	private String seoDesc;

	/**
	 * SEO关键字
	 */
	@Column(name = "seo_keyword", nullable = true, length = 200)
	private String seoKeyword;

	/**
	 * SEO标题
	 */
	@Column(name = "seo_title", nullable = true, length = 200)
	private String seoTitle;

	/**
	 * 活动介绍
	 */
	@Column(name = "act_intro", nullable = true, length = 500)
	private String actIntro;

	/**
	 * 预热开始时间
	 */
	@Column(name = "preheat_time", nullable = true, length = 19)
	private Date preheatTime;

	/**
	 * 活动开始时间
	 */
	@Column(name = "start_time", nullable = true, length = 19)
	private Date startTime;

	/**
	 * 活动结束时间
	 */
	@Column(name = "end_time", nullable = true, length = 19)
	private Date endTime;

	/**
	 * 动态地址
	 */
	@Column(name = "dyn_url", nullable = true, length = 300)
	private String dynUrl;

	/**
	 * 线上地址
	 */
	@Column(name = "live_url", nullable = true, length = 300)
	private String liveUrl;

	/**
	 * UI图片
	 */
	@Column(name = "ui_img", nullable = true, length = 300)
	private String uiImg;

	/**
	 * 备注
	 */
	@Column(name = "remark", nullable = true, length = 300)
	private String remark;

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
	 * 排序值
	 */
	@Column(name = "order_value", nullable = true, length = 19)
	private Long orderValue;

	/**
	 * 页面XML配置
	 */
	@Lob
	@Column(name = "page_config", nullable = true, length = 65535)
	private String pageConfig;

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

	public String getPageCode() {
		return this.pageCode;
	}

	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}

	public String getPageImg() {
		return this.pageImg;
	}

	public void setPageImg(String pageImg) {
		this.pageImg = pageImg;
	}

	public String getAppType() {
		return this.appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getPageType() {
		return this.pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getPageState() {
		return this.pageState;
	}

	public void setPageState(String pageState) {
		this.pageState = pageState;
	}

	public String getSeoDesc() {
		return this.seoDesc;
	}

	public void setSeoDesc(String seoDesc) {
		this.seoDesc = seoDesc;
	}

	public String getSeoKeyword() {
		return this.seoKeyword;
	}

	public void setSeoKeyword(String seoKeyword) {
		this.seoKeyword = seoKeyword;
	}

	public String getSeoTitle() {
		return this.seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getActIntro() {
		return this.actIntro;
	}

	public void setActIntro(String actIntro) {
		this.actIntro = actIntro;
	}

	public Date getPreheatTime() {
		return this.preheatTime;
	}

	public void setPreheatTime(Date preheatTime) {
		this.preheatTime = preheatTime;
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

	public String getDynUrl() {
		return this.dynUrl;
	}

	public void setDynUrl(String dynUrl) {
		this.dynUrl = dynUrl;
	}

	public String getLiveUrl() {
		return this.liveUrl;
	}

	public void setLiveUrl(String liveUrl) {
		this.liveUrl = liveUrl;
	}

	public String getUiImg() {
		return this.uiImg;
	}

	public void setUiImg(String uiImg) {
		this.uiImg = uiImg;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getPageConfig() {
		return this.pageConfig;
	}

	public void setPageConfig(String pageConfig) {
		this.pageConfig = pageConfig;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

