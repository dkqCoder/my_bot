package com.tty.user.controller.model.result;

/**
 * @author zxh
 * @create 2017-04-01
 */
public class UserNameIdResult {
    private Long id;
    private String name;
    private String lastlogintime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(String lastlogintime) {
        this.lastlogintime = lastlogintime;
    }
}
