package com.lord.common.model.mis;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
/**
 * 后台菜单的具体权限mis_menu_right的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年07月28日 18:03:31
 */
@Entity
@Table(name = "mis_menu_right")
public class MisMenuRight implements Serializable {

	public static final String TABLE_NAME = "mis_menu_right";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 权限名称
	 */
	@Column(name = "name", nullable = true, length = 100)
	private String name;

	/**
	 * 权限code
	 */
	@Column(name = "right_code", nullable = true, length = 100)
	private String rightCode;

	/**
	 * 菜单Id
	 */
	@Column(name = "menu_id", nullable = true, length = 19)
	private Long menuId;

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

	public String getRightCode() {
		return this.rightCode;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}

	public Long getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
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

