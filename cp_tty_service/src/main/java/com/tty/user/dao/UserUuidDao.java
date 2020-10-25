package com.tty.user.dao;

import com.tty.user.dao.entity.UserUuidENT;

/**
 * @author shenwei
 * @create 2017-12-25 14:12:48
 **/
public interface UserUuidDao {

    void saveUserUuid(UserUuidENT ent);

    void updateUserUuid(UserUuidENT ent);

    void saveOrUpdateUserUuid(UserUuidENT ent);

    UserUuidENT getUuidUserByUuidCmdName(String uuid, String cmdName);
}