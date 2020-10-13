package com.tty.data.dto;


import java.io.Serializable;

public class BasedataPayTypeDTO implements Serializable {

    /** 主键 */
    private Integer id;

    /** 支付名称 */
    private String name;

    /** 支付类型Code */
    private String code;

    /** 备注：加密类型 */
    private String memo;

    /** 支付请求地址 */
    private String serverUrl;

    /** 支付通知地址（异步） */
    private String noticeAsynUrl;

    /** 支付回调地址(同步) */
    private String noticeSyncUrl;

    /** 是否可用：1-是，0-否 */
    private Integer status;

    /** 排序 */
    private Integer order;

    /** s_customer_name */
    private String customerName;

    /** s_customer_code */
    private String customerCode;

    /** s_md5_key */
    private String md5Key;

    /** s_rsa_public_key */
    private String rsaPublicKey;

    /** s_rsa_private_key */
    private String rsaPrivateKey;

    /** s_des_key */
    private String desKey;




    public Integer  getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String  getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String  getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String  getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String  getNoticeAsynUrl() {
        return noticeAsynUrl;
    }

    public void setNoticeAsynUrl(String noticeAsynUrl) {
        this.noticeAsynUrl = noticeAsynUrl;
    }

    public String  getNoticeSyncUrl() {
        return noticeSyncUrl;
    }

    public void setNoticeSyncUrl(String noticeSyncUrl) {
        this.noticeSyncUrl = noticeSyncUrl;
    }

    public Integer  getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer  getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String  getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String  getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String  getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

    public String  getRsaPublicKey() {
        return rsaPublicKey;
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }

    public String  getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public String  getDesKey() {
        return desKey;
    }

    public void setDesKey(String desKey) {
        this.desKey = desKey;
    }



    @Override
    public String toString() {
        return "BasedataPayTypeDTO{"+"id="+id+",name="+name+",code="+code+",memo="+memo+",serverUrl="+serverUrl+",noticeAsynUrl="+noticeAsynUrl+",noticeSyncUrl="+noticeSyncUrl+",status="+status+",order="+order+",customerName="+customerName+",customerCode="+customerCode+",md5Key="+md5Key+",rsaPublicKey="+rsaPublicKey+",rsaPrivateKey="+rsaPrivateKey+",desKey="+desKey+"}";
    }
}