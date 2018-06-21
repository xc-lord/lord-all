package com.lord.common.model.common;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * 常见问题common_question的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2018年06月16日 18:06:42
 */
@Entity
@Table(name = "common_question")
public class CommonQuestion implements Serializable {

	public static final String TABLE_NAME = "common_question";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 实体ID
	 */
	@Column(name = "entity_id", nullable = true, length = 19)
	private Long entityId;

	/**
	 * 实体编码
	 */
	@Column(name = "entity_code", nullable = true, length = 50)
	private String entityCode;

	/**
	 * 问题
	 */
	@Column(name = "question", nullable = true, length = 200)
	private String question;

	/**
	 * 答案
	 */
	@Column(name = "answer", nullable = true, length = 800)
	private String answer;

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

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEntityId() {
		return this.entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getEntityCode() {
		return this.entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

