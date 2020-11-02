package com.tty.data.controller.vo;

/**
 * @Author wenxiaoqing
 * @Date 2017/10/11
 * @Description
 */
public class ClientStartStopVO {
    private String DGStatus;//是否支持单关
    private String playStatus;//玩法是否开售
    private String optionStatus;//详情开停售状态
    private String SP;//赔率

    public String getDGStatus() {
        return DGStatus;
    }

    public void setDGStatus(String DGStatus) {
        this.DGStatus = DGStatus;
    }

    public String getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(String playStatus) {
        this.playStatus = playStatus;
    }

    public String getOptionStatus() {
        return optionStatus;
    }

    public void setOptionStatus(String optionStatus) {
        this.optionStatus = optionStatus;
    }

    public String getSP() {
        return SP;
    }

    public void setSP(String SP) {
        this.SP = SP;
    }
}
