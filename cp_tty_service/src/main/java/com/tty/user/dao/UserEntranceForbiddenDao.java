package com.tty.user.dao;


import com.alibaba.fastjson.JSONObject;
import com.tty.user.dao.entity.UserEntranceForbiddenENT;

import java.util.List;
import java.util.Map;

/**
 * @author zxh
 * @create 2017-07-28 10:23:53
 **/
public interface UserEntranceForbiddenDao {

    void saveUserEntranceForbidden(UserEntranceForbiddenENT ent);

    void updateUserEntranceForbidden(UserEntranceForbiddenENT ent);

    void saveOrUpdateUserEntranceForbidden(UserEntranceForbiddenENT ent);

    UserEntranceForbiddenENT getUserEntranceForbidden(String cmdName, String province, String city);

    List<UserEntranceForbiddenENT> listUserEntranceForbidden(Integer page, Integer limit, JSONObject data);

    Long listUserEntranceForbiddenCount(JSONObject data);

    void delUserEntranceForbidden(Long id);

    UserEntranceForbiddenENT getUserEntranceForbiddenByProvince(String cmdName, String province);

    UserEntranceForbiddenENT getEntranceForbidden(String cmdName, String province, String city);

    UserEntranceForbiddenENT getById(Long id);

    List<Map<String, String>> listCmdName();

    Boolean isExitstEntranceForbiddenByProvince(String cmdName, String province);

}