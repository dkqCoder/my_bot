package com.tty.ots.trade.dto;

/**
 * Created by liuzp on 2017/4/25.
 */
public class TradeBankCardDTO {
    private int status;         //状态
    private String cityid;      //城市id
    private int bankid;         //银行id
    private long bindid;        //提现绑定的卡号列表id
    private String cardnumber; //银行卡号
    private int cardtype;       //默认提款银行卡 1-是  0-不是
    private String bankname;    //bankName = city.Name + " " + bankName; 城市名称 + 银行名称

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public int getBankid() {
        return bankid;
    }

    public void setBankid(int bankid) {
        this.bankid = bankid;
    }

    public long getBindid() {
        return bindid;
    }

    public void setBindid(long bindid) {
        this.bindid = bindid;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public int getCardtype() {
        return cardtype;
    }

    public void setCardtype(int cardtype) {
        this.cardtype = cardtype;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }
}
