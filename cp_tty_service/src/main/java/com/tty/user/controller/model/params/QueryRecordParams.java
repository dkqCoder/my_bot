package com.tty.user.controller.model.params;

import java.util.Date;

/**
 * Created by jdd on 2017/11/16.
 */
public class QueryRecordParams {
    /** 主键ID */
    private Long id;

    /** 操作人 */
    private String operator = "";

    /** 操作菜单 */
    private String menu = "";

    /** 创建时间 */
    private Date createTime;

    /** 查询条件 */
    private String condition = "";

    /** 系统名称 */
    private String sysCode = "";

    /** 后台系统名称 */
    private String adminCode = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }
}
