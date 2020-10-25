package com.tty.user.dao;

import com.alibaba.fastjson.JSONObject;
import com.tty.user.dao.entity.UserMobileSecbootENT;

import java.util.List;

/**
 * @author zxh
 * @create 2017-07-10 13:16:34
 **/
public interface UserMobileSecbootDao {

    void saveUserMobileSecboot(UserMobileSecbootENT ent);

    void updateUserMobileSecboot(UserMobileSecbootENT ent);

    void saveOrUpdateUserMobileSecboot(UserMobileSecbootENT ent);


//    =========================admin==================================

    List<UserMobileSecbootENT> listUserMobileSecboot(Integer page, Integer limit, JSONObject data);

    Long listUserMobileSecbootCount(JSONObject data);
}