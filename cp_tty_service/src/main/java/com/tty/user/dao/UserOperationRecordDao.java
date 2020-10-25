package com.tty.user.dao;

import com.tty.user.dao.entity.UserOperationRecordENT;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 用户操作记录
 *
 * @author zxh
 * @create 2017-06-27 14:17:24
 **/
public interface UserOperationRecordDao {

    void saveUserOperationRecord(UserOperationRecordENT ent);

    void updateUserOperationRecord(UserOperationRecordENT ent);

    void saveOrUpdateUserOperationRecord(UserOperationRecordENT ent);


//    =========================admin==================================

    List<UserOperationRecordENT> listUserOperationRecord(Integer page, Integer limit, JSONObject data);

    Long listUserOperationRecordCount(JSONObject data);
}