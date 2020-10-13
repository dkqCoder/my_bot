package com.tty.data.dto;


import java.io.Serializable;

public class BasedataBankDTO implements Serializable {

    /** 主键 */
    private Integer bankId;

    /** 银行名称 */
    private String bankName;

    /** 排序号 */
    private Integer order;

    /** 状态：1-正常，0-删除 */
    private Integer status;

    /** 银行对应行号 */
    private String bankCode;






    public Integer  getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String  getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer  getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer  getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Override
    public String toString() {
        return "BasedataBankDTO{"+"bankId="+bankId+",bankName="+bankName+",order="+order+",status="+status+"}";
    }
}