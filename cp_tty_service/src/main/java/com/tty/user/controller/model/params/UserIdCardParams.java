package com.tty.user.controller.model.params;

/**
 * @Author wenxiaoqing
 * @Date 2017/3/22
 * @Description 用户身份证请求参数
 */
public class UserIdCardParams {
    private String idcardnumber; //身份证号码
    private String name; //姓名

    public String getIdcardnumber() {
        return idcardnumber;
    }

    public void setIdcardnumber(String idcardnumber) {
        this.idcardnumber = idcardnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
