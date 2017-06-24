package com.lord.common.model.mis;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by xiaocheng on 2017/3/24.
 */
@Entity
@Table(name = "test_user")
public class MisUserModel implements Serializable {

    /** 主键ID */
    @Id
    @GeneratedValue
    private Long id;

    /** 用户名 */
    @Column(nullable = false)
    private String username;

    /** 密码 */
    @Column(nullable = false)
    private String password;

    /** 手机号 */
    @Column
    private String phone;

    /** 用户名 */
    @Column
    private int sex;

    public MisUserModel() {
    }

    public MisUserModel(String username, int sex) {
        this.username = username;
        this.sex = sex;
        this.password = "123456";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
