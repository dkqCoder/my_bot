package com.tty.user.dao.impl;

import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.tty.user.dao.UserUuidDao;
import com.tty.user.dao.entity.UserUuidENT;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhuxinhai
 */
@Repository("userUuidDao")
public class UserUuidDaoImpl extends BaseDao<UserUuidENT> implements UserUuidDao {

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    public void saveUserUuid(UserUuidENT ent) {
        save(ent);
    }

    public void updateUserUuid(UserUuidENT ent) {
        update(ent);
    }

    public void saveOrUpdateUserUuid(UserUuidENT ent) {
        saveOrUpdate(ent);
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public UserUuidENT getUuidUserByUuidCmdName(String uuid, String cmdName) {
        String hql = "FROM UserUuidENT WHERE uuid = ? and entranceCode = ?";
        List<UserUuidENT> items = find(hql, new Object[]{uuid, cmdName});
        if (CollectionUtils.isEmpty(items)) {
            return null;
        }
        return items.get(0);
    }

}
