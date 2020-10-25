package com.tty.user.service;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.model.ExtModel;
import com.tty.user.dao.entity.UserLoginRecordENT;

/**
 * @author zxh
 * @create 2017-03-06 18:02:14
 **/
public interface UserLoginRecordService {


    //    =========================admin==================================
    ExtModel listUserLoginRecord(JSONObject jsonParm, ExtModel result);

    void saveUserLoginRecord(UserLoginRecordENT UserLoginRecord);

    void updateUserLoginRecord(UserLoginRecordENT UserLoginRecord);
}
