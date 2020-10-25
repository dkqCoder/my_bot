package com.tty.user.dao;

import com.tty.user.dao.pojo.UserIntegralRecords;

import java.util.Date;
import java.util.List;

/**
 * @author chenlongfei
 */
public interface UserIntegralRecordsDao {

    public void saveUserIntegralRecords(UserIntegralRecords ent);

    public void updateUserIntegralRecords(UserIntegralRecords ent);

    public void deleteUserIntegralRecords(UserIntegralRecords ent);

    public void saveOrUpdateUserIntegralRecords(UserIntegralRecords ent);

    List<UserIntegralRecords> listUserIntegralRecords(long userId, Integer pageIndex, Integer pageSize, long lastId);

    List<UserIntegralRecords> listUserIntegralRecordsByTime(long userId, Integer pageIndex, Integer pageSize, Date startTime, Date endTime);

    void removeIntegralRecords();
}
