package com.tty.data.controller.api.vo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author zhangdi
 * @date 17/4/14
 * @Description
 */
public class ClientBonusOptimizeParams {
    private String inputParams;
    private String type;//load,optimize

    private String origin;

    public String getInputParams() {
        return inputParams;
    }

    @JSONField(name = "inputjson")
    public void setInputParams(String inputParams) {
        this.inputParams = inputParams;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
