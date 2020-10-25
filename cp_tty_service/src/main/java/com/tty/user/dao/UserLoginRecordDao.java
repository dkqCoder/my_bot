package com.tty.user.dao;

import com.tty.user.dao.entity.UserLoginRecordENT;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author zxh
 * @create 2017-03-06 18:02:14
 **/
public interface UserLoginRecordDao {

    public void saveUserLoginRecord(UserLoginRecordENT ent);

    public void updateUserLoginRecord(UserLoginRecordENT ent);

    public void deleteUserLoginRecord(UserLoginRecordENT ent);

    public void saveOrUpdateUserLoginRecord(UserLoginRecordENT ent);

    List<UserLoginRecordENT> listUserLoginRecord(Integer page, Integer limit, JSONObject data);

    Long listUserLoginRecordCount(JSONObject data);
}