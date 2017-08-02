package com.lord.common.model.cms;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
/**
 * 文章标签关联表cms_article_tags的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 18:03:02
 */
@Entity
@Table(name = "cms_article_tags")
public class CmsArticleTags implements Serializable {

	public static final String TABLE_NAME = "cms_article_tags";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 文章Id
	 */
	@Column(name = "article_id", length = 19)
	private Long articleId;

	/**
	 * 标签Id
	 */
	@Column(name = "tags_id", length = 19)
	private Long tagsId;

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

	public Long getTagsId() {
		return this.tagsId;
	}

	public void setTagsId(Long tagsId) {
		this.tagsId = tagsId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

