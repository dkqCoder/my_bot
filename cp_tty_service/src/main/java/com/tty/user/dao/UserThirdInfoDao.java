package com.tty.user.dao;

import com.alibaba.fastjson.JSONObject;
import com.tty.user.dao.entity.UserThirdInfoENT;

import java.util.List;

/**
 * @author
 * @create 2017-04-04 11:28:28
 **/
public interface UserThirdInfoDao {

    public void saveUserThirdInfo(UserThirdInfoENT ent);

    public void updateUserThirdInfo(UserThirdInfoENT ent);

    public void deleteUserThirdInfo(UserThirdInfoENT ent);

    public void saveOrUpdateUserThirdInfo(UserThirdInfoENT ent);


//    =========================admin==================================

    List<UserThirdInfoENT> listUserThirdInfo(Integer page, Integer limit, JSONObject data);

    Long listUserThirdInfoCount(JSONObject data);


    UserThirdInfoENT getUserThirdInfoByThirdId(String thirdId, String registerType);
}