package com.tty.user.dto;/**
 * Created by shenwei on 2017/3/8.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-03-08
 */
public class UserDTO {

    @JSONField(name = "ID")
    private Long id;
    @JSONField(name = "SiteID")
    private Integer siteId=0;
    @JSONField(name = "Name")
    private String name="";
    @JSONField(name = "Password")
    private String password="";
    @JSONField(name = "IsCanLogin")
    private Boolean isCanLogin=true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getCanLogin() {
        return isCanLogin;
    }

    public void setCanLogin(Boolean canLogin) {
        isCanLogin = canLogin;
    }
}
