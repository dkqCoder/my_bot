package com.tty.user.controller.model.params;/**
 * Created by shenwei on 2017/3/14.
 */

/**
 * @author shenwei
 * @create 2017-03-14
 */
public class PasswordParams {
    private String oldPw;
    private String newPw;

    public String getOldPw() {
        return oldPw;
    }

    public void setOldPw(String oldPw) {
        this.oldPw = oldPw;
    }

    public String getNewPw() {
        return newPw;
    }

    public void setNewPw(String newPw) {
        this.newPw = newPw;
    }
}
