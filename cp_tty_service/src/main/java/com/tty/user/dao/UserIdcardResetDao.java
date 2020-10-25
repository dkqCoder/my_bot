package com.tty.user.dao;

import com.tty.user.dao.entity.UserIdcardResetENT;
import com.alibaba.fastjson.JSONObject;
import com.tty.user.dto.UserIdcardResetDTO;

import java.util.List;

/**
 * @author zxh
 * @create 2017-03-06 18:03:00
 **/
public interface UserIdcardResetDao {

    void saveUserIdcardReset(UserIdcardResetENT ent);

    void updateUserIdcardReset(UserIdcardResetENT ent);

    void deleteUserIdcardReset(UserIdcardResetENT ent);

    void saveOrUpdateUserIdcardReset(UserIdcardResetENT ent);

    List<UserIdcardResetDTO> listUserIdcardReset(JSONObject data);

    Long getUserIdcardResetCount(JSONObject data);

    Boolean auditUserIdcardReset(Long id, int status,String remark);

    UserIdcardResetENT getUserIdcardReset(String userId, Integer status);
}