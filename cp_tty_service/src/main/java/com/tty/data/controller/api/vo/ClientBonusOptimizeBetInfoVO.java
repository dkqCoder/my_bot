package com.tty.data.controller.api.vo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author zhangdi
 * @date 17/4/17
 * @Description
 */
public class ClientBonusOptimizeBetInfoVO {
    private int[] bia;
    private Double op;//单注奖金
    private int count;//注数

    @JSONField(name="BIA")
    public int[] getBia() {
        return bia;
    }

    public void setBia(int[] bia) {
        this.bia = bia;
    }

    @JSONField(name="OP")
    public Double getOp() {
        return op;
    }

    public void setOp(Double op) {
        this.op = op;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
