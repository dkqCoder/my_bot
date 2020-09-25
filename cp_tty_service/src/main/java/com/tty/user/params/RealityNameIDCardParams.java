package com.tty.user.params;/**
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
    @JSONField(name = "RName")
    public void setRealityName(String realityName) {
        this.realityName = realityName;
    }

    public String getIdCard() {
        return idCard;
    }
    @JSONField(name = "IDCard")
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getNickName() {
        return nickName;
    }
    @JSONField(name = "NickName")
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
