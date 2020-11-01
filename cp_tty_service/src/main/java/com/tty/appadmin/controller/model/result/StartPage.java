package com.tty.appadmin.controller.model.result;

/**
 * @Author: sunyishun
 * @Date: 2020/10/26
 * @Description: 启动页信息
 */
public class StartPage {
    private String picUrl; // 图片素材地址
    private String actionBusiness; // 跳转业务类型
    private String actionParam; // 跳转参数

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getActionBusiness() {
        return actionBusiness;
    }

    public void setActionBusiness(String actionBusiness) {
        this.actionBusiness = actionBusiness;
    }

    public String getActionParam() {
        return actionParam;
    }

    public void setActionParam(String actionParam) {
        this.actionParam = actionParam;
    }
}
