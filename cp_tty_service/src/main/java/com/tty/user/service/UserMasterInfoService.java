package com.tty.user.service;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.model.ExtModel;
import com.tty.common.utils.Result;
import com.tty.user.dao.entity.UserMasterInfoENT;

/**
 * @author zxh
 * @create 2017-03-06 18:02:01
 **/
public interface UserMasterInfoService {


    Result searchMasterByNickName(String nickName, Integer pageNum, Integer pageSize);

    //    =========================admin==================================
    ExtModel listUserMasterInfo(JSONObject jsonParm, ExtModel result);

    void saveUserMasterInfo(UserMasterInfoENT UserMasterInfo);

    void updateUserMasterInfo(UserMasterInfoENT UserMasterInfo);
}
