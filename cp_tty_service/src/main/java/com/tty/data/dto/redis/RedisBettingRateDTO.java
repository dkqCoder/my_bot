package com.tty.data.dto.redis;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author pdl
 * @create 2017-02-08
 * 投注比例dto
 */
public class RedisBettingRateDTO implements java.io.Serializable  {

    @JSONField(name = "WinRate")
    private double winRate;
    @JSONField(name = "DrawRate")
    private double drawRate;
    @JSONField(name = "LossRate")
    private double lossRate;

    public double getWinRate() {
        return winRate;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }

    public double getDrawRate() {
        return drawRate;
    }

    public void setDrawRate(double drawRate) {
        this.drawRate = drawRate;
    }

    public double getLossRate() {
        return lossRate;
    }

    public void setLossRate(double lossRate) {
        this.lossRate = lossRate;
    }
}
