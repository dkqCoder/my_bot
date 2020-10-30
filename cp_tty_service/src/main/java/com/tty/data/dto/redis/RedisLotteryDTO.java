package com.tty.data.dto.redis;

import java.util.List;

/**
 * Created by pdl on 2017/4/14.
 */
public class RedisLotteryDTO {
    /** 彩种ID */
    private Integer lotteryId;

    /** 彩种名称 */
    private String lotteryName;

    /** 彩种简称 */
    private String shortName;

    /** 彩种代码 */
    private String lotteryCode;

    /** 开奖号码标准格式 */
    private String winNumberTemplate;

    /** 最大能追多少期('' 表示不能追 '10:期' 表示能向后追10期 '1:天' 表示高频玩法能追1天内所有期) */
    private Integer maxChaseCount;

    /** 购买截至提前时间(秒) */
    private Integer endbuyBeforeSecond;

    /** 期次开售后多少秒开始执行追号任务 */
    private Integer startbuyAfterChaseSecond;

    /** 期次截至后多少秒开奖 */
    private Integer endissueAfterOpenSecond;

    /** 是否有加奖 */
    private Integer extraWinningsStatus;

    /** 状态（0:停售;1:在售） */
    private Integer status;

    private List<String> tags;

    /**
     * 彩种详情描述
     */
    private String detailDesc;

    /**
     * 彩种描述状态（0不启用，1启用）
     */
    private Integer descStatus;

    /**
     * 彩种图标
     */
    private String lotteryIcon;

    /**
     * 追号状态
     */
    private Integer chaseStatus;

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLotteryCode() {
        return lotteryCode;
    }

    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode;
    }

    public String getWinNumberTemplate() {
        return winNumberTemplate;
    }

    public void setWinNumberTemplate(String winNumberTemplate) {
        this.winNumberTemplate = winNumberTemplate;
    }

    public Integer getMaxChaseCount() {
        return maxChaseCount;
    }

    public void setMaxChaseCount(Integer maxChaseCount) {
        this.maxChaseCount = maxChaseCount;
    }

    public Integer getEndbuyBeforeSecond() {
        return endbuyBeforeSecond;
    }

    public void setEndbuyBeforeSecond(Integer endbuyBeforeSecond) {
        this.endbuyBeforeSecond = endbuyBeforeSecond;
    }

    public Integer getStartbuyAfterChaseSecond() {
        return startbuyAfterChaseSecond;
    }

    public void setStartbuyAfterChaseSecond(Integer startbuyAfterChaseSecond) {
        this.startbuyAfterChaseSecond = startbuyAfterChaseSecond;
    }

    public Integer getEndissueAfterOpenSecond() {
        return endissueAfterOpenSecond;
    }

    public void setEndissueAfterOpenSecond(Integer endissueAfterOpenSecond) {
        this.endissueAfterOpenSecond = endissueAfterOpenSecond;
    }

    public Integer getExtraWinningsStatus() {
        return extraWinningsStatus;
    }

    public void setExtraWinningsStatus(Integer extraWinningsStatus) {
        this.extraWinningsStatus = extraWinningsStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public Integer getDescStatus() {
        return descStatus;
    }

    public void setDescStatus(Integer descStatus) {
        this.descStatus = descStatus;
    }

    public String getLotteryIcon() {
        return lotteryIcon;
    }

    public void setLotteryIcon(String lotteryIcon) {
        this.lotteryIcon = lotteryIcon;
    }

    public Integer getChaseStatus() {
        return chaseStatus;
    }

    public void setChaseStatus(Integer chaseStatus) {
        this.chaseStatus = chaseStatus;
    }

}
