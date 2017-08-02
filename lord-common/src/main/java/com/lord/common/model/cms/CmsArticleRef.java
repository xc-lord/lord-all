package com.lord.common.model.cms;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
/**
 * 关联文章表cms_article_ref的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月01日 18:04:16
 */
@Entity
@Table(name = "cms_article_ref")
public class CmsArticleRef implements Serializable {

	public static final String TABLE_NAME = "cms_article_ref";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 文章Id
	 */
	@Column(name = "article_id", length = 19)
	private Long articleId;

	/**
	 * 关联文章Id
	 */
	@Column(name = "ref_article_id", length = 19)
	private Long refArticleId;

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

	public Long getRefArticleId() {
		return this.refArticleId;
	}

	public void setRefArticleId(Long refArticleId) {
		this.refArticleId = refArticleId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

