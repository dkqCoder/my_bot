package com.tty.user.dao;

import com.alibaba.fastjson.JSONObject;
import com.tty.user.dao.entity.UserStopReasonENT;

import java.util.List;

/**
 * @author
 * @create 2017-04-03 18:37:51
 **/
public interface UserStopReasonDao {

    void saveUserStopReason(UserStopReasonENT ent);

    List<UserStopReasonENT> listUserStopReason(Integer page, Integer limit, JSONObject data);

    Long listUserStopReasonCount(JSONObject data);

}