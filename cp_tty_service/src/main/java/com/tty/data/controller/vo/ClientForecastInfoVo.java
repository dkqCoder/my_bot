package com.tty.data.controller.vo;

/**
 * @author zhangdi
 * @date 17/4/17
 * @Description
 */
public class ClientForecastInfoVo {
    /// <summary>
    /// 命中场数
    /// </summary>
    public int hitCount;

    /// <summary>
    /// 该场数下最小中奖注数
    /// </summary>
    public int minWinCount;

    /// <summary>
    /// 该场数下最大中奖注数
    /// </summary>
    public int maxWinCount;

    /// <summary>
    /// 最少奖金
    /// </summary>
    public double minPrize;

    /// <summary>
    /// 最大奖金
    /// </summary>
    public double maxPrize;

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public int getMinWinCount() {
        return minWinCount;
    }

    public void setMinWinCount(int minWinCount) {
        this.minWinCount = minWinCount;
    }

    public int getMaxWinCount() {
        return maxWinCount;
    }

    public void setMaxWinCount(int maxWinCount) {
        this.maxWinCount = maxWinCount;
    }

    public double getMinPrize() {
        return minPrize;
    }

    public void setMinPrize(double minPrize) {
        this.minPrize = minPrize;
    }

    public double getMaxPrize() {
        return maxPrize;
    }

    public void setMaxPrize(double maxPrize) {
        this.maxPrize = maxPrize;
    }
}
