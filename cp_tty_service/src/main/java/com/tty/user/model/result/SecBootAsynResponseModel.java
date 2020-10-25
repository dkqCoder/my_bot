package com.tty.user.model.result;

/**
 * Created by jdd on 2017/8/14.
 */
public class SecBootAsynResponseModel {
    private boolean success;
    private String retMsg;
    private SecBootDataModel[] data;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public SecBootDataModel[] getData() {
        return data;
    }

    public void setData(SecBootDataModel[] data) {
        this.data = data;
    }
}
