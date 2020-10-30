package com.tty.data.dto.redis;

/**
 * @author pdl
 * @create 2017-02-08
 * 欧赔，亚赔，赔率
 */
public class RedisMainBetOfferDTO {
    private Double lastWin;
    private Double lastDraw;
    private Double lastLoss;
    private Double lastWinRate;
    private Double lastDrawRate;
    private Double lastLossRate;

    public Double getLastWin() {
        return lastWin;
    }

    public void setLastWin(Double lastWin) {
        this.lastWin = lastWin;
    }

    public Double getLastDraw() {
        return lastDraw;
    }

    public void setLastDraw(Double lastDraw) {
        this.lastDraw = lastDraw;
    }

    public Double getLastLoss() {
        return lastLoss;
    }

    public void setLastLoss(Double lastLoss) {
        this.lastLoss = lastLoss;
    }

    public Double getLastWinRate() {
        return lastWinRate;
    }

    public void setLastWinRate(Double lastWinRate) {
        this.lastWinRate = lastWinRate;
    }

    public Double getLastDrawRate() {
        return lastDrawRate;
    }

    public void setLastDrawRate(Double lastDrawRate) {
        this.lastDrawRate = lastDrawRate;
    }

    public Double getLastLossRate() {
        return lastLossRate;
    }

    public void setLastLossRate(Double lastLossRate) {
        this.lastLossRate = lastLossRate;
    }
}
