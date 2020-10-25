package com.tty.user.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author wenxiaoqing
 * @Date 2017/3/24
 * @Description
 */
public class UserIdCardResetInfoDTO {
    /** 身份证号码 */
    @JSONField(name = "IdCardNumber")
    private String idcardNumber;
    /** 真实姓名 */
    @JSONField(name = "Name")
    private String realName;
    /** 待审0，成功1，失败2 */
    @JSONField(name = "Status")
    private Integer status;

    public String getIdcardNumber() {
        return idcardNumber;
    }

    public void setIdcardNumber(String idcardNumber) {
        this.idcardNumber = idcardNumber;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
