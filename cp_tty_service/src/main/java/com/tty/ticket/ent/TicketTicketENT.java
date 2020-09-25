package com.tty.ticket.ent;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 电子票
 *
 * @author yys
 * @create 2017-07-04 17:26:13
 **/

@Entity
@Table(name = "ticket_ticket")
@IdClass(TicketTicketPK.class)
public class TicketTicketENT implements Serializable {

    /**
     * 方案id
     */
    private Long schemeId;

    /**
     * 票号
     */
    private String ticketNo;

    /**
     * 商户号
     */
    private Integer merchantId;

    /**
     * 出票商Id
     */
    private Integer agentId;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 注数
     */
    private Integer unitCount;

    /**
     * 倍数
     */
    private Integer multiple;

    /**
     * 票内容
     */
    private String content;

    /**
     * 发送次数
     */
    private Integer sendCount;

    /**
     * 票反馈SP
     */
    private String printSp;

    /**
     * 过关状态：0待过关 1已中奖 2未中奖
     */
    private Integer passStatus;

    /**
     * 发送时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    /**
     * 出票截至时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date printEndTime;

    /**
     * 商户税后中奖金额
     */
    private BigDecimal merchantWinmoneyNotax;

    /**
     * 商户过关时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date merchantPassTime;

    /**
     * 撤单状态
     */
    private Integer quashStatus;

    /**
     * 期次名
     */
    private String issueName;

    /**
     * 彩种id
     */
    private Integer lotteryId;

    /**
     * 玩法id
     */
    private Integer playtypeId;

    /**
     * 出票状态
     */
    private Integer printStatus;

    /**
     * 出票描述
     */
    private String printDesc;

    /**
     * 出票时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date printTime;

    /**
     * 落地票号
     */
    private String printTicketNo;

    /**
     * 反馈状态
     */
    private Integer feedbackStatus;

    /**
     * 反馈时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date feedbackTime;

    /**
     * 出票商中奖金额
     */
    private BigDecimal agentWinmoneyNotax;

    /**
     * 更新时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    /**
     * 获取 方案id 的值
     *
     * @return Long
     */
    @Id
    @Column(name = "n_scheme_id")
    public Long getSchemeId() {
        return schemeId;
    }

    /**
     * 设置方案id 的值
     *
     * @param schemeId 方案id
     */
    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }

    /**
     * 获取 票号 的值
     *
     * @return String
     */
    @Id
    @Column(name = "s_ticket_no")
    public String getTicketNo() {
        return ticketNo;
    }

    /**
     * 设置票号 的值
     *
     * @param ticketNo 票号
     */
    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    /**
     * 获取 商户号 的值
     *
     * @return Integer
     */
    @Column(name = "n_merchant_id")
    public Integer getMerchantId() {
        return merchantId;
    }

    /**
     * 设置商户号 的值
     *
     * @param merchantId 商户号
     */
    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * 获取 出票商code 的值
     *
     * @return Integer
     */
    @Column(name = "n_agent_id")
    public Integer getAgentId() {
        return agentId;
    }

    /**
     * 设置出票商code 的值
     *
     * @param agentCode 出票商code
     */
    public void setAgentId(Integer agentCode) {
        this.agentId = agentCode;
    }

    /**
     * 获取 金额 的值
     *
     * @return BigDecimal
     */
    @Column(name = "n_money")
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置金额 的值
     *
     * @param money 金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取 注数 的值
     *
     * @return Integer
     */
    @Column(name = "n_unit_count")
    public Integer getUnitCount() {
        return unitCount;
    }

    /**
     * 设置注数 的值
     *
     * @param unitCount 注数
     */
    public void setUnitCount(Integer unitCount) {
        this.unitCount = unitCount;
    }

    /**
     * 获取 倍数 的值
     *
     * @return Integer
     */
    @Column(name = "n_multiple")
    public Integer getMultiple() {
        return multiple;
    }

    /**
     * 设置倍数 的值
     *
     * @param multiple 倍数
     */
    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }

    /**
     * 获取 票内容 的值
     *
     * @return String
     */
    @Column(name = "s_content")
    public String getContent() {
        return content;
    }

    /**
     * 设置票内容 的值
     *
     * @param content 票内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取 发送次数 的值
     *
     * @return Integer
     */
    @Column(name = "n_send_count")
    public Integer getSendCount() {
        return sendCount;
    }

    /**
     * 设置发送次数 的值
     *
     * @param sendCount 发送次数
     */
    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    /**
     * 获取 发送时间 的值
     *
     * @return Date
     */
    @Column(name = "d_send_time")
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 设置发送时间 的值
     *
     * @param sendTime 发送时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取 出票截至时间 的值
     *
     * @return Date
     */
    @Column(name = "d_print_end_time")
    public Date getPrintEndTime() {
        return printEndTime;
    }

    /**
     * 设置出票截至时间 的值
     *
     * @param printEndTime 出票截至时间
     */
    public void setPrintEndTime(Date printEndTime) {
        this.printEndTime = printEndTime;
    }

    /**
     * 获取 商户税后中奖金额 的值
     *
     * @return BigDecimal
     */
    @Column(name = "n_merchant_winmoney_notax")
    public BigDecimal getMerchantWinmoneyNotax() {
        return merchantWinmoneyNotax;
    }

    /**
     * 设置商户税后中奖金额 的值
     *
     * @param merchantWinmoneyNotax 商户税后中奖金额
     */
    public void setMerchantWinmoneyNotax(BigDecimal merchantWinmoneyNotax) {
        this.merchantWinmoneyNotax = merchantWinmoneyNotax;
    }

    /**
     * 获取 商户过关时间 的值
     *
     * @return Date
     */
    @Column(name = "d_merchant_pass_time")
    public Date getMerchantPassTime() {
        return merchantPassTime;
    }

    /**
     * 设置商户过关时间 的值
     *
     * @param merchantPassTime 商户过关时间
     */
    public void setMerchantPassTime(Date merchantPassTime) {
        this.merchantPassTime = merchantPassTime;
    }

    /**
     * 获取 撤单状态 的值
     *
     * @return Integer
     */
    @Column(name = "n_quash_status")
    public Integer getQuashStatus() {
        return quashStatus;
    }

    /**
     * 设置撤单状态 的值
     *
     * @param quashStatus 撤单状态
     */
    public void setQuashStatus(Integer quashStatus) {
        this.quashStatus = quashStatus;
    }

    /**
     * 获取 期次名 的值
     *
     * @return String
     */
    @Column(name = "s_issue_name")
    public String getIssueName() {
        return issueName;
    }

    /**
     * 设置期次名 的值
     *
     * @param issueName 期次名
     */
    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    /**
     * 获取 彩种id 的值
     *
     * @return Integer
     */
    @Column(name = "n_lottery_id")
    public Integer getLotteryId() {
        return lotteryId;
    }

    /**
     * 设置彩种id 的值
     *
     * @param lotteryId 彩种id
     */
    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    /**
     * 获取 玩法id 的值
     *
     * @return Integer
     */
    @Column(name = "n_playtype_id")
    public Integer getPlaytypeId() {
        return playtypeId;
    }

    /**
     * 设置玩法id 的值
     *
     * @param playtypeId 玩法id
     */
    public void setPlaytypeId(Integer playtypeId) {
        this.playtypeId = playtypeId;
    }

    /**
     * 获取 出票状态 的值
     *
     * @return Integer
     */
    @Column(name = "n_print_status")
    public Integer getPrintStatus() {
        return printStatus;
    }

    /**
     * 设置出票状态 的值
     *
     * @param printStatus 出票状态
     */
    public void setPrintStatus(Integer printStatus) {
        this.printStatus = printStatus;
    }

    /**
     * 获取 出票描述 的值
     *
     * @return String
     */
    @Column(name = "s_print_desc")
    public String getPrintDesc() {
        return printDesc;
    }

    /**
     * 设置出票描述 的值
     *
     * @param printDesc 出票描述
     */
    public void setPrintDesc(String printDesc) {
        this.printDesc = printDesc;
    }

    /**
     * 获取 出票时间 的值
     *
     * @return Date
     */
    @Column(name = "d_print_time")
    public Date getPrintTime() {
        return printTime;
    }

    /**
     * 设置出票时间 的值
     *
     * @param printTime 出票时间
     */
    public void setPrintTime(Date printTime) {
        this.printTime = printTime;
    }

    /**
     * 获取 落地票号 的值
     *
     * @return String
     */
    @Column(name = "s_print_ticket_no")
    public String getPrintTicketNo() {
        return printTicketNo;
    }

    /**
     * 设置落地票号 的值
     *
     * @param printTicketNo 落地票号
     */
    public void setPrintTicketNo(String printTicketNo) {
        this.printTicketNo = printTicketNo;
    }

    /**
     * 获取 反馈状态 的值
     *
     * @return Integer
     */
    @Column(name = "n_feedback_status")
    public Integer getFeedbackStatus() {
        return feedbackStatus;
    }

    /**
     * 设置反馈状态 的值
     *
     * @param feedbackStatus 反馈状态
     */
    public void setFeedbackStatus(Integer feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }

    /**
     * 获取 反馈时间 的值
     *
     * @return Date
     */
    @Column(name = "d_feedback_time")
    public Date getFeedbackTime() {
        return feedbackTime;
    }

    /**
     * 设置反馈时间 的值
     *
     * @param feedbackTime 反馈时间
     */
    public void setFeedbackTime(Date feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    /**
     * 获取 出票商中奖金额 的值
     *
     * @return BigDecimal
     */
    @Column(name = "n_agent_winmoney_notax")
    public BigDecimal getAgentWinmoneyNotax() {
        return agentWinmoneyNotax;
    }

    /**
     * 设置出票商中奖金额 的值
     *
     * @param agentWinmoneyNotax 出票商中奖金额
     */
    public void setAgentWinmoneyNotax(BigDecimal agentWinmoneyNotax) {
        this.agentWinmoneyNotax = agentWinmoneyNotax;
    }

    /**
     * 获取 更新时间 的值
     *
     * @return Date
     */
    @Column(name = "d_update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间 的值
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取 创建时间 的值
     *
     * @return Date
     */
    @Column(name = "d_create_time")
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间 的值
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "s_print_sp")
    public String getPrintSp() {
        return printSp;
    }

    public void setPrintSp(String printSp) {
        this.printSp = printSp;
    }

    @Column(name = "n_pass_status")
    public Integer getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(Integer passStatus) {
        this.passStatus = passStatus;
    }

}