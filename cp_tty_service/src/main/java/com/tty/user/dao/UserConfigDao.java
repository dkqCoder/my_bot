package com.tty.user.dao;


import com.alibaba.fastjson.JSONObject;
import com.tty.user.dao.entity.UserConfigENT;

import java.util.List;

/**
 * @author zxh
 * @create 2017-08-24 14:17:57
 **/
public interface UserConfigDao {

    UserConfigENT getUserConfig(String key);

    void saveUserConfig(UserConfigENT ent);

    void updateUserConfig(UserConfigENT ent);

    void saveOrUpdateUserConfig(UserConfigENT ent);


//    =========================admin==================================

    List<UserConfigENT> listUserConfig(Integer page, Integer limit, JSONObject data);

    Long listUserConfigCount(JSONObject data);
}