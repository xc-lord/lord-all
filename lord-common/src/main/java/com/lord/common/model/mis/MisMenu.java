package com.lord.common.model.mis;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import com.lord.common.dto.Category;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * 系统菜单mis_menu的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月10日 15:51:31
 */
@Entity
@Table(name = "mis_menu")
public class MisMenu implements Serializable,Category {

	public static final String TABLE_NAME = "mis_menu";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 名称
	 */
	@Column(name = "name", length = 40)
	private String name;

	/**
	 * 等级
	 */
	@Column(name = "level", nullable = true, length = 10)
	private Integer level;

	/**
	 * 拼音
	 */
	@Column(name = "letter", nullable = true, length = 10)
	private String letter;

	/**
	 * 菜单图标
	 */
	@Column(name = "icon", nullable = true, length = 40)
	private String icon;

	/**
	 * 父节点Id
	 */
	@Column(name = "parent_id", nullable = true, length = 19)
	private Long parentId;

	/**
	 * 父节点名称
	 */
	@Column(name = "parent_name", nullable = true, length = 40)
	private String parentName;

	/**
	 * 所有父节点Id
	 */
	@Column(name = "parent_ids", nullable = true, length = 500)
	private String parentIds;

	/**
	 * 是否叶子节点
	 */
	@Column(name = "leaf", nullable = true, length = 1)
	private Boolean leaf;

	/**
	 * 所有子节点Id
	 */
	@Column(name = "children_ids", nullable = true, length = 500)
	private String childrenIds;

	/**
	 * 排序
	 */
	@Column(name = "order_value", nullable = true, length = 19)
	private Long orderValue;

	/**
	 * 菜单链接
	 */
	@Column(name = "url", nullable = true, length = 40)
	private String url;

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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getLetter() {
		return this.letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public Long getOrderValue() {
		return this.orderValue;
	}

	public void setOrderValue(Long orderValue) {
		this.orderValue = orderValue;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
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

