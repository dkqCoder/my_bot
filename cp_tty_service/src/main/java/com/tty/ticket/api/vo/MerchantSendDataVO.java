package com.tty.ticket.api.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class MerchantSendDataVO {
    @JSONField(name = "tickets")
    private List<MerchantSendDataItemVO> data;

    public List<MerchantSendDataItemVO> getData() {
        return data;
    }

    public void setData(List<MerchantSendDataItemVO> data) {
        this.data = data;
    }

}
