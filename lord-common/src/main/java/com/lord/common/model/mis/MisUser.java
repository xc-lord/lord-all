package com.lord.common.model.mis;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * 后台用户mis_user的JPA对象
 *
 * @author xiaocheng
 * @version 1.0
 * @Date 2017年05月09日 16:43:46
 */
@Entity
@Table(name = "mis_user")
public class MisUser implements Serializable {

	public static final String TABLE_NAME = "mis_user";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 真实姓名
	 */
	@Column(name = "name", nullable = true, length = 40)
	private String name;

	/**
	 * 用户名
	 */
	@Column(name = "username", length = 40)
	private String username;

	/**
	 * 密码
	 */
	@Column(name = "password", length = 40)
	private String password;

	/**
	 * 手机
	 */
	@Column(name = "phone", nullable = true, length = 20)
	private String phone;

	/**
	 * 用户状态
	 */
	@Column(name = "status", nullable = true, length = 40)
	private String status;

	/**
	 * 昵称
	 */
	@Column(name = "nick_name", nullable = true, length = 40)
	private String nickName;

	/**
	 * 邮箱
	 */
	@Column(name = "email", nullable = true, length = 50)
	private String email;

	/**
	 * 头像
	 */
	@Column(name = "icon", nullable = true, length = 100)
	private String icon;

	/**
	 * 用户角色
	 */
	@Column(name = "role_name", nullable = true, length = 40)
	private String roleName;

	/**
	 * 用户角色Id
	 */
	@Column(name = "role_id", nullable = true, length = 19)
	private Long roleId;

	/**
	 * 添加用户Id
	 */
	@Column(name = "add_user_id", nullable = true, length = 19)
	private Long addUserId;

	/**
	 * 登录时间
	 */
	@Column(name = "login_time", nullable = true, length = 19)
	private Date loginTime;

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

	/**
	 * 性别：1男，2女
	 */
	@Column(name = "sex", nullable = true, length = 10)
	private Integer sex;

	/**
	 * 是否超级管理员
	 */
	@Column(name = "super_admin", length = 1)
	private Boolean superAdmin;

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

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getAddUserId() {
		return this.addUserId;
	}

	public void setAddUserId(Long addUserId) {
		this.addUserId = addUserId;
	}

	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
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

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Boolean isSuperAdmin() {
		return this.superAdmin;
	}
	public Boolean getSuperAdmin() {
		return this.superAdmin;
	}

	public void setSuperAdmin(Boolean superAdmin) {
		this.superAdmin = superAdmin;
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

