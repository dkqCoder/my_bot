package com.tty.user.dao;

import com.alibaba.fastjson.JSONObject;
import com.tty.user.dao.entity.UserFilterDetailENT;

import java.util.List;

/**
 * @Author chenpengfei {chenpengfei@jiangduoduo.com}
 * @Date 2017/10/30 17:29
 */
public interface UserFilterDetailDao {
    void addUserFilterDetail(UserFilterDetailENT userFilterDetailENT);

    void updateUserFilter(UserFilterDetailENT userFilterDetailENT);

    List<UserFilterDetailENT> findUserFilterDetailList(UserFilterDetailENT userFilterDetailENT, List<Long> userFilterIds);

    //    =========================admin==================================

    List<UserFilterDetailENT> listUserFilterDetail(Integer page, Integer limit, JSONObject data);

    boolean userWhiteListExists(String uuid,String ip);

    Long getUserFilterDetailCount(JSONObject data);
}
