package com.tty.user.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by linian on 2017/5/10.
 */
public class UserRedLogoInfoDTO {

    @JSONField(name = "IsNewPersonGive")
    private Integer isNewPersonGive;

    @JSONField(name = "IsBindMobile")
    private Integer isBindMobile;

    @JSONField(name = "IsBindBank")
    private Integer isBindBank;

    @JSONField(name = "FirstBuy")
    private Integer firstBuy;

    @JSONField(name = "IsCrazyRobHongBao")
    private Integer isCrazyRobHongBao;

    @JSONField(name = "WinCount")
    private Integer winCount;

    @JSONField(name = "ChaseCount")
    private Integer chaseCount;

    @JSONField(name = "NewCzCardNum")
    private Integer newCzCardNum;

    @JSONField(name = "HbAddedCount")
    private Integer hbAddedCount;

    @JSONField(name = "BuyStatus20")
    private Integer buyStatus20;

    @JSONField(name = "BuyStatus118")
    private Integer buyStatus118;

    @JSONField(name = "AwardStatus")
    private Integer awardStatus;

    @JSONField(name = "NewsCount")
    private Integer newsCount;

    @JSONField(name = "NextSignInExperience")
    private Integer nextSignInExperience;

    @JSONField(name = "FreeWelfareInfo")
    private FreeWelfareInfoDTO freeWelfareInfo;

    @JSONField(name = "FreeWelfareCount")
    private Integer freeWelfareCount;

    @JSONField(name = "HomeHave")
    private Integer homeHave;

    @JSONField(name = "UserSign")
    private UserSignDTO userSign;

    public Integer getIsNewPersonGive() {
        return isNewPersonGive;
    }

    public void setIsNewPersonGive(Integer isNewPersonGive) {
        this.isNewPersonGive = isNewPersonGive;
    }

    public Integer getIsBindMobile() {
        return isBindMobile;
    }

    public void setIsBindMobile(Integer isBindMobile) {
        this.isBindMobile = isBindMobile;
    }

    public Integer getIsBindBank() {
        return isBindBank;
    }

    public void setIsBindBank(Integer isBindBank) {
        this.isBindBank = isBindBank;
    }

    public Integer getFirstBuy() {
        return firstBuy;
    }

    public void setFirstBuy(Integer firstBuy) {
        this.firstBuy = firstBuy;
    }

    public Integer getIsCrazyRobHongBao() {
        return isCrazyRobHongBao;
    }

    public void setIsCrazyRobHongBao(Integer isCrazyRobHongBao) {
        this.isCrazyRobHongBao = isCrazyRobHongBao;
    }

    public Integer getWinCount() {
        return winCount;
    }

    public void setWinCount(Integer winCount) {
        this.winCount = winCount;
    }

    public Integer getChaseCount() {
        return chaseCount;
    }

    public void setChaseCount(Integer chaseCount) {
        this.chaseCount = chaseCount;
    }

    public Integer getNewCzCardNum() {
        return newCzCardNum;
    }

    public void setNewCzCardNum(Integer newCzCardNum) {
        this.newCzCardNum = newCzCardNum;
    }

    public Integer getHbAddedCount() {
        return hbAddedCount;
    }

    public void setHbAddedCount(Integer hbAddedCount) {
        this.hbAddedCount = hbAddedCount;
    }

    public Integer getBuyStatus20() {
        return buyStatus20;
    }

    public void setBuyStatus20(Integer buyStatus20) {
        this.buyStatus20 = buyStatus20;
    }

    public Integer getBuyStatus118() {
        return buyStatus118;
    }

    public void setBuyStatus118(Integer buyStatus118) {
        this.buyStatus118 = buyStatus118;
    }

    public Integer getAwardStatus() {
        return awardStatus;
    }

    public void setAwardStatus(Integer awardStatus) {
        this.awardStatus = awardStatus;
    }

    public Integer getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(Integer newsCount) {
        this.newsCount = newsCount;
    }

    public Integer getNextSignInExperience() {
        return nextSignInExperience;
    }

    public void setNextSignInExperience(Integer nextSignInExperience) {
        this.nextSignInExperience = nextSignInExperience;
    }

    public FreeWelfareInfoDTO getFreeWelfareInfo() {
        return freeWelfareInfo;
    }

    public void setFreeWelfareInfo(FreeWelfareInfoDTO freeWelfareInfo) {
        this.freeWelfareInfo = freeWelfareInfo;
    }

    public Integer getFreeWelfareCount() {
        return freeWelfareCount;
    }

    public void setFreeWelfareCount(Integer freeWelfareCount) {
        this.freeWelfareCount = freeWelfareCount;
    }

    public Integer getHomeHave() {
        return homeHave;
    }

    public void setHomeHave(Integer homeHave) {
        this.homeHave = homeHave;
    }

    public UserSignDTO getUserSign() {
        return userSign;
    }

    public void setUserSign(UserSignDTO userSign) {
        this.userSign = userSign;
    }
}
