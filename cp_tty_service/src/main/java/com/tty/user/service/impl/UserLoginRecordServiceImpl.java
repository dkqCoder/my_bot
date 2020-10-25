package com.tty.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.model.ExtModel;
import com.tty.user.dao.UserLoginRecordDao;
import com.tty.user.dao.entity.UserLoginRecordENT;
import com.tty.user.service.UserLoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userLoginRecordService")
public class UserLoginRecordServiceImpl implements UserLoginRecordService {
    @Autowired
    private UserLoginRecordDao userLoginRecordDao;


    //    =========================admin==================================
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public ExtModel listUserLoginRecord(JSONObject jsonParm, ExtModel result) {
        result.setData(userLoginRecordDao.listUserLoginRecord(jsonParm.getInteger("page"), jsonParm.getInteger("limit"), jsonParm.getJSONObject("data")));
        result.setTotal(userLoginRecordDao.listUserLoginRecordCount(jsonParm.getJSONObject("data")));
        return result;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void saveUserLoginRecord(UserLoginRecordENT userLoginRecord) {
        userLoginRecordDao.saveUserLoginRecord(userLoginRecord);
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void updateUserLoginRecord(UserLoginRecordENT userLoginRecord) {
        userLoginRecordDao.updateUserLoginRecord(userLoginRecord);
    }
}
