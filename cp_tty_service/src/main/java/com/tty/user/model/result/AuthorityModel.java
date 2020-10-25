package com.tty.user.model.result;/**
 * Created by shenwei on 2017/1/6.
 */

/**
 * @author shenwei
 * @create 2017-01-06
 */
public class AuthorityModel {

    private Integer giftid;
    private String name;
    private String dis;
    private Integer category;
    private Integer btnStatus;
    private String btnText;

    public Integer getGiftid() {
        return giftid;
    }

    public void setGiftid(Integer giftid) {
        this.giftid = giftid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public Integer getBtnStatus() {
        return btnStatus;
    }

    public void setBtnStatus(Integer btnStatus) {
        this.btnStatus = btnStatus;
    }

    public String getBtnText() {
        return btnText;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
