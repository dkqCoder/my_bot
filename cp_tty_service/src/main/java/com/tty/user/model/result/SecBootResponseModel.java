package com.tty.user.model.result;

/**
 * Created by linian on 2017/6/21.
 */
public class SecBootResponseModel {
    private boolean success;
    private String retMsg;
    private SecBootDataModel data;

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

    public SecBootDataModel getData() {
        return data;
    }

    public void setData(SecBootDataModel data) {
        this.data = data;
    }
}
