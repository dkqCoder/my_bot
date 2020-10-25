package com.tty.user.controller.model.params;


/**
 * 第三方自动登录
 */
public class ThirdAutomaticLoginParams {
    /**
     * 三方appID
     */
    private String appKey;
    /**
     * 三方用户ID
     */
    private String uid;
    /**
     * 三方昵称
     */
    private String nickName;
    /**
     * 三方头像URL
     */
    private String imageUrl;
    /**
     * 三方手机号
     */
    private String mobilePhone;
    /**
     * 时间戳
     */
    private String timeStamp;
    /**
     * 附加信息
     */
    private String attach;
    /**
     * 签名
     */
    private String sign;
    /**
     * 签名类型
     */
    private String signType;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }
}
