package com.tty.appadmin.controller.model.result;

/**
 * @Author: sunyishun
 * @Date: 2020/10/27
 * @Description: 首页投注彩种信息
 */
public class IndexLottery {
    private int lotteryId;
    private String lotteryName; // 彩种名称
    private String lotteryTitle; // 彩种主标题
    private String lotterySecondTitle; // 彩种副标题
    private String iconUrl; // 彩种图标
    private String actionBusiness; // 跳转业务类型
    private String actionParam; // 跳转参数

    public IndexLottery() {
    }

    public IndexLottery(int lotteryId, String lotteryName, String lotteryTitle, String lotterySecondTitle, String iconUrl, String actionBusiness, String actionParam) {
        this.lotteryId = lotteryId;
        this.lotteryName = lotteryName;
        this.lotteryTitle = lotteryTitle;
        this.lotterySecondTitle = lotterySecondTitle;
        this.iconUrl = iconUrl;
        this.actionBusiness = actionBusiness;
        this.actionParam = actionParam;
    }

    public int getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getLotteryTitle() {
        return lotteryTitle;
    }

    public void setLotteryTitle(String lotteryTitle) {
        this.lotteryTitle = lotteryTitle;
    }

    public String getLotterySecondTitle() {
        return lotterySecondTitle;
    }

    public void setLotterySecondTitle(String lotterySecondTitle) {
        this.lotterySecondTitle = lotterySecondTitle;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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
