package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2017/3/28.
 */

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author shenwei
 * @create 2017-03-28
 */
public class RealityNameIDCardParams {
    private String realityName;
    private String idCard;
    private String nickName;

    public String getRealityName() {
        return realityName;
    }
    public void setRealityName(String realityName) {
        this.realityName = realityName;
    }

    public String getIdCard() {
        return idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
