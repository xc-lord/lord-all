package com.lord.common.model.mis;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
/**
 * 角色权限管理mis_role_right的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年08月01日 15:44:25
 */
@Entity
@Table(name = "mis_role_right")
public class MisRoleRight implements Serializable {

	public static final String TABLE_NAME = "mis_role_right";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 角色Id
	 */
	@Column(name = "role_id", nullable = true, length = 19)
	private Long roleId;

	/**
	 * 菜单Id
	 */
	@Column(name = "menu_id", nullable = true, length = 19)
	private Long menuId;

	/**
	 * 权限Id
	 */
	@Column(name = "right_id", nullable = true, length = 19)
	private Long rightId;

	/**
	 * 权限code
	 */
	@Column(name = "right_code", nullable = true, length = 200)
	private String rightCode;

	/**
	 * 是否为菜单权限
	 */
	@Column(name = "menu_right", nullable = true, length = 1)
	private Boolean menuRight;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getRightId() {
		return this.rightId;
	}

	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}

	public String getRightCode() {
		return this.rightCode;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}

	public Boolean isMenuRight() {
		return this.menuRight;
	}
	public Boolean getMenuRight() {
		return this.menuRight;
	}

	public void setMenuRight(Boolean menuRight) {
		this.menuRight = menuRight;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

