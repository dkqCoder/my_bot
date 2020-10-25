package com.tty.user.service.impl;

import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.tty.common.utils.Result;
import com.tty.user.dao.UserIntegralRecordsDao;
import com.tty.user.dao.pojo.UserIntegralRecords;
import com.tty.user.model.result.UserIntegralRecordModel;
import com.tty.user.model.result.UserIntegralRecordResult;
import com.tty.user.service.UserIntegralCoreService;
import com.tty.user.service.UserIntegralRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by shenwei on 2016/12/14.
 * 用户积分明细
 */

@Service("userIntegralRecordService")
public class UserIntegralRecordServiceImpl implements UserIntegralRecordService {

    private static final Logger logger = LoggerFactory.getLogger(UserIntegralRecordServiceImpl.class);

    @Autowired
    private UserIntegralRecordsDao userIntegralRecordsDao;
    @Autowired
    private UserIntegralCoreService userIntegralCoreService;

    /**
     * @Author shenwei
     * @Date 2016/12/21 14:55
     * @Description 获取用户积分收支明细
     */
    @Override
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    public Result getUserInteralRecords(Long userId, Integer pageIndex, Integer pageSize, Long lastId) throws Exception {
        Result result = new Result();
        try {
            UserIntegralRecordResult model = new UserIntegralRecordResult();
            //缓存中获取
            model.setSysdate(System.currentTimeMillis());
            model.setIntegral(Integer.parseInt(userIntegralCoreService.getUserIntegralBalance(userId).toString()));
            //获取用户积分明细
            List<UserIntegralRecords> list = userIntegralRecordsDao.listUserIntegralRecords(userId, pageIndex, pageSize, lastId);
            if (list != null && list.size() > 0) {
                List<UserIntegralRecordModel> items = new ArrayList<>();
                for (UserIntegralRecords item : list) {
                    UserIntegralRecordModel record = new UserIntegralRecordModel();
                    Date createDate = item.getCreateDate();
                    record.setId(item.getId());
                    record.setDate(createDate.getTime());
                    record.setDiscription(item.getDescription());
                    record.setIntegralChange(item.getCount().toString());
                    items.add(record);
                }
                model.setDetails(items);
            } else {
                model.setDetails(new ArrayList<>());
            }
            result.setData(model);
            result.setCode(Result.SUCCESS);
            result.setMsg(Result.MSG_SUCCESS_DESC);
        } catch (Exception ex) {
            result.setCode(Result.ERROR);
            result.setMsg(Result.MSG_ERROR_DESC);
            logger.error("获取用户积分收支明细失败！【userId:{};StackTrace: {}】", userId, LogExceptionStackTrace.erroStackTrace(ex));
            throw ex;
        }
        return result;
    }


    @Override
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Transactional(readOnly = false)
    public void saveUserIntegralRecords(UserIntegralRecords ent) {
        userIntegralRecordsDao.saveUserIntegralRecords(ent);
    }

    @Override
    public void removeIntegralRecords() {
        logger.info("清理积分明细记录数据");
        userIntegralRecordsDao.removeIntegralRecords();
    }
}
