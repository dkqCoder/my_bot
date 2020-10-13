package com.tty.data.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhangdi
 * @date 17/4/21
 * @Description
 */
public class BasedataWinTypeDTO implements Serializable {
    /** 主键 */
    private Integer id;

    /** 彩种ID */
    private Integer lotteryId;

    /** 奖等名称 */
    private String wintypeName;

    /** 默认的中奖金额 */
    private BigDecimal defaultWinmoney;

    /** 默认的中奖税后金额 */
    private BigDecimal defaultWinmoneyNotax;

    /** 加奖金额 */
    private BigDecimal extraWinningsAmount;

    /** 加奖税后奖金 */
    private BigDecimal extraWinningsAmountNotax;

    /** 是否是浮动奖金 */
    private Integer floatWinningsStatus;

    /** 顺序 */
    private Integer order;

    /** 状态：1-有效，0-无效 */
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getWintypeName() {
        return wintypeName;
    }

    public void setWintypeName(String wintypeName) {
        this.wintypeName = wintypeName;
    }

    public BigDecimal getDefaultWinmoney() {
        return defaultWinmoney;
    }

    public void setDefaultWinmoney(BigDecimal defaultWinmoney) {
        this.defaultWinmoney = defaultWinmoney;
    }

    public BigDecimal getDefaultWinmoneyNotax() {
        return defaultWinmoneyNotax;
    }

    public void setDefaultWinmoneyNotax(BigDecimal defaultWinmoneyNotax) {
        this.defaultWinmoneyNotax = defaultWinmoneyNotax;
    }

    public BigDecimal getExtraWinningsAmount() {
        return extraWinningsAmount;
    }

    public void setExtraWinningsAmount(BigDecimal extraWinningsAmount) {
        this.extraWinningsAmount = extraWinningsAmount;
    }

    public Integer getFloatWinningsStatus() {
        return floatWinningsStatus;
    }

    public void setFloatWinningsStatus(Integer floatWinningsStatus) {
        this.floatWinningsStatus = floatWinningsStatus;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getExtraWinningsAmountNotax() {
        return extraWinningsAmountNotax;
    }

    public void setExtraWinningsAmountNotax(BigDecimal extraWinningsAmountNotax) {
        this.extraWinningsAmountNotax = extraWinningsAmountNotax;
    }
}
