package com.lord.common.model.cms;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * 文章cms_article的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 18:09:10
 */
@Entity
@Table(name = "cms_article")
public class CmsArticle implements Serializable {

	public static final String TABLE_NAME = "cms_article";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 文章标题
	 */
	@Column(name = "title", length = 200)
	private String title;

	/**
	 * 副标题
	 */
	@Column(name = "title_sub", nullable = true, length = 200)
	private String titleSub;

	/**
	 * 封面
	 */
	@Column(name = "cover_img", nullable = true, length = 300)
	private String coverImg;

	/**
	 * 文章简介
	 */
	@Column(name = "intro", nullable = true, length = 300)
	private String intro;

	/**
	 * 作者Id
	 */
	@Column(name = "author_id", nullable = true, length = 19)
	private Long authorId;

	/**
	 * 作者头像
	 */
	@Column(name = "author_img", nullable = true, length = 100)
	private String authorImg;

	/**
	 * 作者名称
	 */
	@Column(name = "author_name", nullable = true, length = 100)
	private String authorName;

	/**
	 * 来源
	 */
	@Column(name = "source", nullable = true, length = 100)
	private String source;

	/**
	 * 来源链接
	 */
	@Column(name = "source_url", nullable = true, length = 300)
	private String sourceUrl;

	/**
	 * 文章标签
	 */
	@Column(name = "tags", nullable = true, length = 200)
	private String tags;

	/**
	 * 文章标签json
	 */
	@Column(name = "tags_json", nullable = true, length = 300)
	private String tagsJson;

	/**
	 * 站点id
	 */
	@Column(name = "site_id", nullable = true, length = 19)
	private Long siteId;

	/**
	 * 分类id
	 */
	@Column(name = "cat_id", nullable = true, length = 19)
	private Long catId;

	/**
	 * 分类名称
	 */
	@Column(name = "cat_name", nullable = true, length = 50)
	private String catName;

	/**
	 * 一级分类
	 */
	@Column(name = "cat_one_id", nullable = true, length = 19)
	private Long catOneId;

	/**
	 * 二级分类
	 */
	@Column(name = "cat_two_id", nullable = true, length = 19)
	private Long catTwoId;

	/**
	 * 三级分类
	 */
	@Column(name = "cat_three_id", nullable = true, length = 19)
	private Long catThreeId;

	/**
	 * 审核状态
	 */
	@Column(name = "check_state", nullable = true, length = 50)
	private String checkState;

	/**
	 * 文章状态
	 */
	@Column(name = "state", nullable = true, length = 50)
	private String state;

	/**
	 * 样式
	 */
	@Column(name = "style", nullable = true, length = 50)
	private String style;

	/**
	 * 发布时间
	 */
	@Column(name = "publish_time", nullable = true, length = 19)
	private Date publishTime;

	/**
	 * 添加用户Id
	 */
	@Column(name = "mis_user_id", nullable = true, length = 19)
	private Long misUserId;

	/**
	 * 用户Id
	 */
	@Column(name = "user_id", nullable = true, length = 19)
	private Long userId;

	/**
	 * 是否允许评论
	 */
	@Column(name = "allow_comment", nullable = true, length = 1)
	private Boolean allowComment;

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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleSub() {
		return this.titleSub;
	}

	public void setTitleSub(String titleSub) {
		this.titleSub = titleSub;
	}

	public String getCoverImg() {
		return this.coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String getIntro() {
		return this.intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Long getAuthorId() {
		return this.authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getAuthorImg() {
		return this.authorImg;
	}

	public void setAuthorImg(String authorImg) {
		this.authorImg = authorImg;
	}

	public String getAuthorName() {
		return this.authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceUrl() {
		return this.sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTagsJson() {
		return this.tagsJson;
	}

	public void setTagsJson(String tagsJson) {
		this.tagsJson = tagsJson;
	}

	public Long getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Long getCatId() {
		return this.catId;
	}

	public void setCatId(Long catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return this.catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public Long getCatOneId() {
		return this.catOneId;
	}

	public void setCatOneId(Long catOneId) {
		this.catOneId = catOneId;
	}

	public Long getCatTwoId() {
		return this.catTwoId;
	}

	public void setCatTwoId(Long catTwoId) {
		this.catTwoId = catTwoId;
	}

	public Long getCatThreeId() {
		return this.catThreeId;
	}

	public void setCatThreeId(Long catThreeId) {
		this.catThreeId = catThreeId;
	}

	public String getCheckState() {
		return this.checkState;
	}

	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Date getPublishTime() {
		return this.publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
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

	public Boolean isAllowComment() {
		return this.allowComment;
	}
	public Boolean getAllowComment() {
		return this.allowComment;
	}

	public void setAllowComment(Boolean allowComment) {
		this.allowComment = allowComment;
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

