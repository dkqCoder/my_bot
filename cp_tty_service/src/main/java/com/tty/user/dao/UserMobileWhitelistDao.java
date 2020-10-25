package com.tty.user.dao;


import com.alibaba.fastjson.JSONObject;
import com.tty.user.dao.entity.UserMobileWhitelistENT;

import java.util.List;

/**
 * @author zxh
 * @create 2017-08-23 11:09:55
 **/
public interface UserMobileWhitelistDao {

    void saveUserMobileWhitelist(UserMobileWhitelistENT ent);

    void updateUserMobileWhitelist(UserMobileWhitelistENT ent);

    void saveOrUpdateUserMobileWhitelist(UserMobileWhitelistENT ent);

    UserMobileWhitelistENT getMobileWhiteList(String mobile);

    void deleteMobileWhitelist(String mobile);

    void deleteMobileWhitelist(Long id);

    List<UserMobileWhitelistENT> listMobileWhitelist(Integer page, Integer limit, JSONObject data);

    Long listMobileWhitelistCount(JSONObject data);
}