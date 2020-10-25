package com.tty.user.service;


import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.model.ExtModel;
import com.tty.user.dao.entity.UserEntranceDenyTimeENT;
import com.tty.user.dao.entity.UserEntranceForbiddenENT;
import com.tty.user.dao.entity.UserMobileWhitelistENT;

import java.util.List;
import java.util.Map;

/**
 * @Author shenwei
 * @Date 2017/7/28 10:45
 * @Description
 */
public interface UserEntranceForbiddenService {

    Integer filterEntrance(String userId, String mobile, String cmdName, String traceId);

    ExtModel listUserEntranceForbidden(JSONObject jsonParm);

    void delete(Long id);

    int saveUserEntranceForbidden(UserEntranceForbiddenENT ent);

    void updateUserEntranceForbidden(UserEntranceForbiddenENT ent);

    List<Map<String, String>> listCmdName();

    void deleteMobileWhitelist(String mobile);

    void deleteMobileWhitelist(Long id);

    void saveUserMobileWhitelist(UserMobileWhitelistENT ent);

    void batchSaveUserMobileWhitelist(String mobiles);

    void batchDelUserMobileWhitelist(String ids);

    ExtModel listMobileWhitelist(JSONObject jsonParm);

    void saveEntranceDenyTimelist(UserEntranceDenyTimeENT ent);

    ExtModel listEntranceDenyTime(JSONObject jsonParm);

    void deleteEntranceDenyTime(Long id);
}
