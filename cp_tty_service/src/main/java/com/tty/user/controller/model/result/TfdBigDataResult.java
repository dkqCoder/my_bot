package com.tty.user.controller.model.result;/**
 * Created by shenwei on 2017/12/12.
 */

/**
 * @author shenwei
 * @create 2017-12-12
 */
public class TfdBigDataResult {
    private boolean checkFlag;
    private String userId;

    public boolean isCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
