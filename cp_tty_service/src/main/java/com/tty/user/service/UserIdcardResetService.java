package com.tty.user.service;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.model.ExtModel;
import com.tty.user.dao.entity.UserIdcardResetENT;

/**
 * @author zxh
 * @create 2017-03-06 18:03:00
 **/
public interface UserIdcardResetService{

    ExtModel listUserIdcardReset(JSONObject jsonParm);

    void saveUserIdcardReset(UserIdcardResetENT UserIdcardReset);

    void updateUserIdcardReset(UserIdcardResetENT UserIdcardReset);

    ExtModel auditUserIdcardReset(Long id, Long userId, String idcardNumber, String realName, int status, String remark);
}
