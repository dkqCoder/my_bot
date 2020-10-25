package com.tty.user.dto;

/**
 * @Author: sunyishun
 * @Date: 2020/10/19
 * @Description: 图形验证码
 */
public class PicCaptchaDTO {
    String key;
    String image;

    public PicCaptchaDTO() {
    }

    public PicCaptchaDTO(String key, String image) {
        this.key = key;
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
