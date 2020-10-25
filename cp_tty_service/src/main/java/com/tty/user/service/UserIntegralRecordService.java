package com.tty.user.service;


import com.tty.common.utils.Result;
import com.tty.user.dao.pojo.UserIntegralRecords;

/**
 * Created by shenwei on 2016/12/14.
 * 用户积分明细
 */
public interface UserIntegralRecordService {
    Result getUserInteralRecords(Long userId, Integer pageIndex, Integer pageSize, Long lastId) throws Exception;

    void saveUserIntegralRecords(UserIntegralRecords ent);

    void removeIntegralRecords();
}
