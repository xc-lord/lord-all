package com.lord.common.model.cms;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * 文章内容cms_article_content的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月08日 15:04:19
 */
@Entity
@Table(name = "cms_article_content")
public class CmsArticleContent implements Serializable {

	public static final String TABLE_NAME = "cms_article_content";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 文章Id
	 */
	@Column(name = "article_id", length = 19)
	private Long articleId;

	/**
	 * 文章内容
	 */
	@Lob
	@Column(name = "content", nullable = true, length = 65535)
	private String content;

	/**
	 * 编辑的内容
	 */
	@Lob
	@Column(name = "content_edit", nullable = true, length = 65535)
	private String contentEdit;

	/**
	 * 移动端内容
	 */
	@Lob
	@Column(name = "m_content", nullable = true, length = 65535)
	private String mcontent;

	/**
	 * 移动端编辑的内容
	 */
	@Lob
	@Column(name = "m_content_edit", nullable = true, length = 65535)
	private String mcontentEdit;

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

	public Long getArticleId() {
		return this.articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentEdit() {
		return this.contentEdit;
	}

	public void setContentEdit(String contentEdit) {
		this.contentEdit = contentEdit;
	}

	public String getMcontent() {
		return this.mcontent;
	}

	public void setMcontent(String mcontent) {
		this.mcontent = mcontent;
	}

	public String getMcontentEdit() {
		return this.mcontentEdit;
	}

	public void setMcontentEdit(String mcontentEdit) {
		this.mcontentEdit = mcontentEdit;
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

