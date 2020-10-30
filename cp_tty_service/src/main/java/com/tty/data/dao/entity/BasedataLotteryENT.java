package com.tty.data.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 **/

@Entity
@Table(name = "basedata_lottery")
public class BasedataLotteryENT implements java.io.Serializable {

    /**
     * 彩种ID
     */
    private Integer lotteryId;

    /**
     * 彩种名称
     */
    private String lotteryName;

    /**
     * 彩种简称
     */
    private String shortName;

    /**
     * 彩种代码
     */
    private String lotteryCode;

    /**
     * 开奖号码标准格式
     */
    private String winNumberTemplate;

    /**
     * 最大能追多少期('' 表示不能追 '10:期' 表示能向后追10期 '1:天' 表示高频玩法能追1天内所有期)
     */
    private Integer maxChaseCount;

    /**
     * 购买截至提前时间(秒)
     */
    private Integer endbuyBeforeSecond;

    /**
     * 期次开售后多少秒开始执行追号任务
     */
    private Integer startbuyAfterChaseSecond;

    /**
     * 期次截至后多少秒开奖
     */
    private Integer endissueAfterOpenSecond;

    /**
     * 是否有加奖
     */
    private Integer extraWinningsStatus;

    /**
     * 状态（0:停售;1:在售）
     */
    private Integer status;

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

    private String maxIssueName;

    private String endDate;

    /**
     * 追号状态
     */
    private Integer chaseStatus;

    @Transient
    public String getMaxIssueName() {
        return maxIssueName;
    }

    public void setMaxIssueName(String maxIssueName) {
        this.maxIssueName = maxIssueName;
    }

    @Transient
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    /**
     * 获取 彩种ID 的值
     *
     * @return Integer
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_lottery_id")
    public Integer getLotteryId() {
        return lotteryId;
    }

    /**
     * 设置彩种ID 的值
     *
     * @param lotteryId 彩种ID
     */
    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    /**
     * 获取 彩种名称 的值
     *
     * @return String
     */
    @Column(name = "s_lottery_name")
    public String getLotteryName() {
        return lotteryName;
    }

    /**
     * 设置彩种名称 的值
     *
     * @param lotteryName 彩种名称
     */
    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    /**
     * 获取 彩种简称 的值
     *
     * @return String
     */
    @Column(name = "s_short_name")
    public String getShortName() {
        return shortName;
    }

    /**
     * 设置彩种简称 的值
     *
     * @param shortName 彩种简称
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * 获取 彩种代码 的值
     *
     * @return String
     */
    @Column(name = "s_lottery_code")
    public String getLotteryCode() {
        return lotteryCode;
    }

    /**
     * 设置彩种代码 的值
     *
     * @param lotteryCode 彩种代码
     */
    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode;
    }

    /**
     * 获取 开奖号码标准格式 的值
     *
     * @return String
     */
    @Column(name = "s_win_number_template")
    public String getWinNumberTemplate() {
        return winNumberTemplate;
    }

    /**
     * 设置开奖号码标准格式 的值
     *
     * @param winNumberTemplate 开奖号码标准格式
     */
    public void setWinNumberTemplate(String winNumberTemplate) {
        this.winNumberTemplate = winNumberTemplate;
    }

    /**
     * 获取 最大能追多少期('' 表示不能追 '10:期' 表示能向后追10期 '1:天' 表示高频玩法能追1天内所有期) 的值
     *
     * @return Integer
     */
    @Column(name = "n_max_chase_count")
    public Integer getMaxChaseCount() {
        return maxChaseCount;
    }

    /**
     * 设置最大能追多少期('' 表示不能追 '10:期' 表示能向后追10期 '1:天' 表示高频玩法能追1天内所有期) 的值
     *
     * @param maxChaseCount 最大能追多少期('' 表示不能追 '10:期' 表示能向后追10期 '1:天' 表示高频玩法能追1天内所有期)
     */
    public void setMaxChaseCount(Integer maxChaseCount) {
        this.maxChaseCount = maxChaseCount;
    }

    /**
     * 获取 购买截至提前时间(秒) 的值
     *
     * @return Integer
     */
    @Column(name = "n_endbuy_before_second")
    public Integer getEndbuyBeforeSecond() {
        return endbuyBeforeSecond;
    }

    /**
     * 设置购买截至提前时间(秒) 的值
     *
     * @param endbuyBeforeSecond 购买截至提前时间(秒)
     */
    public void setEndbuyBeforeSecond(Integer endbuyBeforeSecond) {
        this.endbuyBeforeSecond = endbuyBeforeSecond;
    }

    /**
     * 获取 期次开售后多少秒开始执行追号任务 的值
     *
     * @return Integer
     */
    @Column(name = "n_startbuy_after_chase_second")
    public Integer getStartbuyAfterChaseSecond() {
        return startbuyAfterChaseSecond;
    }

    /**
     * 设置期次开售后多少秒开始执行追号任务 的值
     *
     * @param startbuyAfterChaseSecond 期次开售后多少秒开始执行追号任务
     */
    public void setStartbuyAfterChaseSecond(Integer startbuyAfterChaseSecond) {
        this.startbuyAfterChaseSecond = startbuyAfterChaseSecond;
    }

    /**
     * 获取 期次截至后多少秒开奖 的值
     *
     * @return Integer
     */
    @Column(name = "n_endissue_after_open_second")
    public Integer getEndissueAfterOpenSecond() {
        return endissueAfterOpenSecond;
    }

    /**
     * 设置期次截至后多少秒开奖 的值
     *
     * @param endissueAfterOpenSecond 期次截至后多少秒开奖
     */
    public void setEndissueAfterOpenSecond(Integer endissueAfterOpenSecond) {
        this.endissueAfterOpenSecond = endissueAfterOpenSecond;
    }

    /**
     * 获取 是否有加奖 的值
     *
     * @return Integer
     */
    @Column(name = "n_extra_winnings_status")
    public Integer getExtraWinningsStatus() {
        return extraWinningsStatus;
    }

    /**
     * 设置是否有加奖 的值
     *
     * @param extraWinningsStatus 是否有加奖
     */
    public void setExtraWinningsStatus(Integer extraWinningsStatus) {
        this.extraWinningsStatus = extraWinningsStatus;
    }

    /**
     * 获取 状态（0:停售;1:在售） 的值
     *
     * @return Integer
     */
    @Column(name = "n_status")
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态（0:停售;1:在售） 的值
     *
     * @param status 状态（0:停售;1:在售）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取彩种详情描述信息
     *
     * @return
     */
    @Column(name = "s_detail_desc")
    public String getDetailDesc() {
        return detailDesc;
    }

    /**
     * 备注彩种详情内容描述
     *
     * @param detailDesc
     */
    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    /**
     * 获取状态（0不启用，1启用）
     *
     * @param
     */
    @Column(name = "n_desc_status")
    public Integer getDescStatus() {
        return descStatus;
    }

    /**
     * 设置状态（0不启用，1启用）
     *
     * @param descStatus
     */
    public void setDescStatus(Integer descStatus) {
        this.descStatus = descStatus;
    }

    /**
     * 获取追号状态（0关闭，1开启）
     */
    @Column(name = "n_chase_status")
    public Integer getChaseStatus() {
        return chaseStatus;
    }

    /**
     * 设置追号状态（0关闭，1开启）
     * @param chaseStatus
     */
    public void setChaseStatus(Integer chaseStatus) {
        this.chaseStatus = chaseStatus;
    }

    @Column(name = "s_lottery_icon")
    public String getLotteryIcon() {
        return lotteryIcon;
    }

    public void setLotteryIcon(String lotteryIcon) {
        this.lotteryIcon = lotteryIcon;
    }

}