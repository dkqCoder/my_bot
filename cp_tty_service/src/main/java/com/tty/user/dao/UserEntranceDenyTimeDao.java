package com.tty.user.dao;

import com.alibaba.fastjson.JSONObject;
import com.tty.user.dao.entity.UserEntranceDenyTimeENT;

import java.util.List;


/**
 * @author zxh
 * @create 2017-08-24 14:17:57
 **/
public interface UserEntranceDenyTimeDao {

    void saveUserEntranceDenyTime(UserEntranceDenyTimeENT ent);

    void updateUserEntranceDenyTime(UserEntranceDenyTimeENT ent);

    void saveOrUpdateUserEntranceDenyTime(UserEntranceDenyTimeENT ent);

    Boolean isInEntranceDenyTimeRegion(String cmdName);

    Boolean notInEntranceDenyTimeRegionWithcmdName(String cmdName);


//    =========================admin==================================

    List<UserEntranceDenyTimeENT> listUserEntranceDenyTime(Integer page, Integer limit, JSONObject data);

    Long listUserEntranceDenyTimeCount(JSONObject data);

    void deleteEntranceDenyTime(Long id);
}