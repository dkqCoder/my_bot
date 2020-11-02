package com.tty.appadmin.controller.model.result;

/**
 * @Author: sunyishun
 * @Date: 2020/10/26
 * @Description: 首页图片广告信息
 */
public class IndexPicNotice {
    int indexPicNoticeId; // ID
    int status; // 1:显示 2：隐藏
    private String picUrl; // 图片素材地址
    private String actionBusiness; // 跳转业务类型
    private String actionParam; // 跳转参数

    public IndexPicNotice() {
    }

    public IndexPicNotice(int indexPicNoticeId, int status, String picUrl, String actionBusiness, String actionParam) {
        this.indexPicNoticeId = indexPicNoticeId;
        this.status = status;
        this.picUrl = picUrl;
        this.actionBusiness = actionBusiness;
        this.actionParam = actionParam;
    }

    public int getIndexPicNoticeId() {
        return indexPicNoticeId;
    }

    public void setIndexPicNoticeId(int indexPicNoticeId) {
        this.indexPicNoticeId = indexPicNoticeId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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
