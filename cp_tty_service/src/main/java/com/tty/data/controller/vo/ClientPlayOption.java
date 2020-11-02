package com.tty.data.controller.vo;


/**
 * @Author wenxiaoqing
 * @Date 2017/10/11
 * @Description
 */
public class ClientPlayOption {
    private ClientStartStopVO SPF;
    private ClientStartStopVO RQSPF;
    private ClientStartStopVO BQC;
    private ClientStartStopVO CBF;
    private ClientStartStopVO ZJQ;

    public ClientStartStopVO getSPF() {
        return SPF;
    }

    public void setSPF(ClientStartStopVO SPF) {
        this.SPF = SPF;
    }

    public ClientStartStopVO getRQSPF() {
        return RQSPF;
    }

    public void setRQSPF(ClientStartStopVO RQSPF) {
        this.RQSPF = RQSPF;
    }

    public ClientStartStopVO getBQC() {
        return BQC;
    }

    public void setBQC(ClientStartStopVO BQC) {
        this.BQC = BQC;
    }

    public ClientStartStopVO getCBF() {
        return CBF;
    }

    public void setCBF(ClientStartStopVO CBF) {
        this.CBF = CBF;
    }

    public ClientStartStopVO getZJQ() {
        return ZJQ;
    }

    public void setZJQ(ClientStartStopVO ZJQ) {
        this.ZJQ = ZJQ;
    }
}
