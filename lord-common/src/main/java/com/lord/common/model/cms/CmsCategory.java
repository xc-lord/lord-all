package com.lord.common.model.cms;

import com.lord.common.dto.cat.Category;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * 文章分类cms_category的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 16:25:37
 */
@Entity
@Table(name = "cms_category")
public class CmsCategory implements Serializable, Category {

	public static final String TABLE_NAME = "cms_category";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 分类名称
	 */
	@Column(name = "name", length = 50)
	private String name;

	/**
	 * 拼音
	 */
	@Column(name = "letter", nullable = true, length = 100)
	private String letter;

	/**
	 * 站点id
	 */
	@Column(name = "site_id", nullable = true, length = 19)
	private Long siteId;

	/**
	 * 等级
	 */
	@Column(name = "level", nullable = true, length = 10)
	private Integer level;

	/**
	 * 描述
	 */
	@Column(name = "intro", nullable = true, length = 300)
	private String intro;

	/**
	 * 分类图标
	 */
	@Column(name = "icon", nullable = true, length = 300)
	private String icon;

	/**
	 * PC图片
	 */
	@Column(name = "pc_img", nullable = true, length = 300)
	private String pcImg;

	/**
	 * 移动端图片
	 */
	@Column(name = "mobile_img", nullable = true, length = 300)
	private String mobileImg;

	/**
	 * 父分类
	 */
	@Column(name = "parent_id", nullable = true, length = 19)
	private Long parentId;

	/**
	 * 父分类名称
	 */
	@Column(name = "parent_name", nullable = true, length = 100)
	private String parentName;

	/**
	 * 父分类Id列表
	 */
	@Column(name = "parent_ids", nullable = true, length = 100)
	private String parentIds;

	/**
	 * 是否是叶子节点
	 */
	@Column(name = "leaf", nullable = true, length = 1)
	private Boolean leaf;

	/**
	 * 子分类列表
	 */
	@Column(name = "children_ids", nullable = true, length = 200)
	private String childrenIds;

	/**
	 * 样式
	 */
	@Column(name = "style", nullable = true, length = 50)
	private String style;

	/**
	 * 添加用户Id
	 */
	@Column(name = "mis_user_id", nullable = true, length = 19)
	private Long misUserId;

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

	public String getLetter() {
		return this.letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public Long getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPcImg() {
		return this.pcImg;
	}

	public void setPcImg(String pcImg) {
		this.pcImg = pcImg;
	}

	public String getMobileImg() {
		return this.mobileImg;
	}

	public void setMobileImg(String mobileImg) {
		this.mobileImg = mobileImg;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentIds() {
		return this.parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public Boolean isLeaf() {
		return this.leaf;
	}
	public Boolean getLeaf() {
		return this.leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public String getChildrenIds() {
		return this.childrenIds;
	}

	public void setChildrenIds(String childrenIds) {
		this.childrenIds = childrenIds;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Long getMisUserId() {
		return this.misUserId;
	}

	public void setMisUserId(Long misUserId) {
		this.misUserId = misUserId;
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

