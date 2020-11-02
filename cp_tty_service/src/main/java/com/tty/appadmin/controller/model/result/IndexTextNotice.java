package com.tty.appadmin.controller.model.result;

/**
 * @Author: sunyishun
 * @Date: 2020/10/27
 * @Description: 首页文字栏信息：（平台系统公告，大奖滚动播报）
 */
public class IndexTextNotice {
    private int indexTextNoticeId; // ID
    private String text; // 文字内容
    private String actionBusiness; // 跳转业务类型
    private String actionParam; // 跳转参数

    public IndexTextNotice() {
    }

    public IndexTextNotice(int indexTextNoticeId, String text, String actionBusiness, String actionParam) {
        this.indexTextNoticeId = indexTextNoticeId;
        this.text = text;
        this.actionBusiness = actionBusiness;
        this.actionParam = actionParam;
    }

    public int getIndexTextNoticeId() {
        return indexTextNoticeId;
    }

    public void setIndexTextNoticeId(int indexTextNoticeId) {
        this.indexTextNoticeId = indexTextNoticeId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
