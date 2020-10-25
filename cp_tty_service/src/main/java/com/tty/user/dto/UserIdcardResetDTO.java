package com.tty.user.dto;

public class UserIdcardResetDTO{

    private int id;

    /** userid */
    private Long userId;

    private String userName;

    /** 身份证号码 */
    private String idcardNumber;

    /** 真实姓名 */
    private String realName;

    /** 身份证号码 */
    private String oldIdcardNumber;

    /** 真实姓名 */
    private String oldRealName;

    /** 身份证正面照地址 */
    private String frontUrl;

    /** 身份证背面照地址 */
    private String backUrl;

    /** 待审0，成功1，失败2 */
    private Integer status;

    /** d_create_time */
    private String createTime;

    /** d_update_time */
    private String updateTime;

    /** s_remark */
    private String remark;


    public Long  getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String  getIdcardNumber() {
        return idcardNumber;
    }

    public void setIdcardNumber(String idcardNumber) {
        this.idcardNumber = idcardNumber;
    }

    public String  getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String  getFrontUrl() {
        return frontUrl;
    }

    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    public String  getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public Integer  getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String  getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String  getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String  getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOldIdcardNumber() {
        return oldIdcardNumber;
    }

    public void setOldIdcardNumber(String oldIdcardNumber) {
        this.oldIdcardNumber = oldIdcardNumber;
    }

    public String getOldRealName() {
        return oldRealName;
    }

    public void setOldRealName(String oldRealName) {
        this.oldRealName = oldRealName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}