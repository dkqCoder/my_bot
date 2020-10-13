package com.tty.data.dto;

/**
 * 玩法信息dubbo传输类
 *
 * @author wumingchun
 * @date 2017 05 03 18:04
 */
public class BasedataPlayTypeDTO implements java.io.Serializable {
    private Integer playTypeId;
    private Integer lotteryId;
    private String playTypeName;
    private String playTypeCode;

    public Integer getPlayTypeId() {
        return playTypeId;
    }

    public void setPlayTypeId(Integer playTypeId) {
        this.playTypeId = playTypeId;
    }

    public Integer getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(Integer lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getPlayTypeName() {
        return playTypeName;
    }

    public void setPlayTypeName(String playTypeName) {
        this.playTypeName = playTypeName;
    }

    public String getPlayTypeCode() {
        return playTypeCode;
    }

    public void setPlayTypeCode(String playTypeCode) {
        this.playTypeCode = playTypeCode;
    }
}
