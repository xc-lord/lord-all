package com.lord.common.model.ads;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * 广告位的元素ads_element的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月03日 16:18:22
 */
@Entity
@Table(name = "ads_element")
public class AdsElement implements Serializable {

	public static final String TABLE_NAME = "ads_element";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 名称
	 */
	@Column(name = "name", nullable = true, length = 300)
	private String name;

	/**
	 * 标题
	 */
	@Column(name = "title", nullable = true, length = 300)
	private String title;

	/**
	 * 子标题
	 */
	@Column(name = "sub_title", nullable = true, length = 300)
	private String subTitle;

	/**
	 * 图片地址
	 */
	@Column(name = "ads_img", nullable = true, length = 300)
	private String adsImg;

	/**
	 * 跳转的链接
	 */
	@Column(name = "ads_url", nullable = true, length = 300)
	private String adsUrl;

	/**
	 * 标签
	 */
	@Column(name = "tags", nullable = true, length = 300)
	private String tags;

	/**
	 * 扩展属性
	 */
	@Column(name = "extension", nullable = true, length = 300)
	private String extension;

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
	 * 排序值
	 */
	@Column(name = "order_value", nullable = true, length = 19)
	private Long orderValue;

	/**
	 * 关键字
	 */
	@Column(name = "keyword", length = 200)
	private String keyword;

	/**
	 * 元素id
	 */
	@Column(name = "target_id", nullable = true, length = 100)
	private String targetId;

	/**
	 * 元素类型
	 */
	@Column(name = "target_type", length = 100)
	private String targetType;

	/**
	 * 页面id
	 */
	@Column(name = "page_id", length = 19)
	private Long pageId;

	/**
	 * 广告位Id
	 */
	@Column(name = "space_id", length = 19)
	private Long spaceId;

	/**
	 * 大文本
	 */
	@Lob
	@Column(name = "tex", nullable = true, length = 65535)
	private String tex;

	/**
	 * 是否高亮
	 */
	@Column(name = "fouce", nullable = true, length = 1)
	private Boolean fouce;

	/**
	 * 是否新窗口打开
	 */
	@Column(name = "target", nullable = true, length = 1)
	private Boolean target;

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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return this.subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getAdsImg() {
		return this.adsImg;
	}

	public void setAdsImg(String adsImg) {
		this.adsImg = adsImg;
	}

	public String getAdsUrl() {
		return this.adsUrl;
	}

	public void setAdsUrl(String adsUrl) {
		this.adsUrl = adsUrl;
	}

	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getExtension() {
		return this.extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
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

	public Long getOrderValue() {
		return this.orderValue;
	}

	public void setOrderValue(Long orderValue) {
		this.orderValue = orderValue;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTargetId() {
		return this.targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getTargetType() {
		return this.targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public Long getPageId() {
		return this.pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public Long getSpaceId() {
		return this.spaceId;
	}

	public void setSpaceId(Long spaceId) {
		this.spaceId = spaceId;
	}

	public String getTex() {
		return this.tex;
	}

	public void setTex(String tex) {
		this.tex = tex;
	}

	public Boolean isFouce() {
		return this.fouce;
	}
	public Boolean getFouce() {
		return this.fouce;
	}

	public void setFouce(Boolean fouce) {
		this.fouce = fouce;
	}

	public Boolean isTarget() {
		return this.target;
	}
	public Boolean getTarget() {
		return this.target;
	}

	public void setTarget(Boolean target) {
		this.target = target;
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

