package com.fms.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/4/9.
 */
public class User implements java.io.Serializable {

    public static final Integer TYPE_ADMIN = 1;
    public static final Integer TYPE_USER = 0;
    public static final Integer DISABLE = 1;
    public static final Integer NO_DISABLE = 0;

    private Integer userId;
    private String userName;
    private String password;
    private Integer userType;
    private Integer disable;
    private Date createTime;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getDisable() {
        return disable;
    }

    public void setDisable(Integer disable) {
        this.disable = disable;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
