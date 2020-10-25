package com.tty.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.model.ExtModel;
import com.jdd.fm.core.utils.DateUtil;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.StringUtils;
import com.tty.user.context.UserFilterContext;
import com.tty.user.dao.UserFilterDao;
import com.tty.user.dao.UserInfoDao;
import com.tty.user.dao.entity.UserFilterDetailENT;
import com.tty.user.dao.entity.UserFilterENT;
import com.tty.user.dao.entity.UserInfoENT;
import com.tty.user.service.UserBaseInfoService;
import com.tty.user.service.UserFilterDetailService;
import com.tty.user.service.UserFilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author chenpengfei {chenpengfei@jiangduoduo.com}
 * @Date 2017/10/30 16:20
 */
@Service("userFilterService")
public class UserFilterServiceImpl implements UserFilterService {

    private static final Logger logger = LoggerFactory.getLogger(UserFilterServiceImpl.class);

    @Autowired
    private UserFilterDao userFilterDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserBaseInfoService userBaseInfoService;

    @Autowired
    private UserFilterDetailService userFilterDetailService;

    private final String reason = "防刷机制禁止登录";

    @Transactional
    @DataSource
    @Override
    public void saveUserFilter(UserFilterENT userFilterENT) {
        userFilterDao.saveUserFilter(userFilterENT);
    }

    @Transactional
    @DataSource
    @Override
    public List<UserFilterENT> findUserFilterList(UserFilterENT userFilterENT) {
        return userFilterDao.findUserFilterList(userFilterENT);
    }

    @Transactional
    @DataSource
    @Override
    public void checkAndForbiddenUser(Integer queryHour) {
        List<Long> fobbidenList = Lists.newLinkedList();
        List<UserInfoENT> userList = userInfoDao.getUserInfoByDate(queryHour);

        UserFilterENT userFilterQuery = new UserFilterENT();
        userFilterQuery.setStatus(UserFilterContext.USER_FILTER_STATUS_TRUE);
        userFilterQuery.setType(UserFilterContext.USER_FILTER_LIST_BLACK);
        userFilterQuery.setEndTime(DateUtil.nowDate());
        List<UserFilterENT> filterList = userFilterDao.findUserFilterList(userFilterQuery);

        List<Long> userFilterIds = Lists.newLinkedList();
        filterList.forEach(ent -> userFilterIds.add(ent.getId()));

        UserFilterDetailENT query = new UserFilterDetailENT();
        query.setStatus(UserFilterContext.USER_FILTER_STATUS_TRUE);
        query.setType(UserFilterContext.USER_FILTER_LIST_BLACK);
        List<UserFilterDetailENT> filterDetailList = userFilterDetailService.findUserFilterDetailList(query, userFilterIds);


        userList.forEach(userInfoENT -> {
            boolean matchFlag = filterDetailList.stream().anyMatch(userFilterDetailENT ->

                    userFilterDetailENT.getContext().equals(userInfoENT.getIpAddress())
                            || userFilterDetailENT.getContext().equals(userInfoENT.getUuid())
                            || userFilterDetailENT.getContext().equals(userInfoENT.getPassword())
                            || userFilterDetailENT.getContext().equals(userInfoENT.getPlatformCode())
                            || userFilterDetailENT.getContext().equals(userInfoENT.getPhoneName()));

            if (matchFlag) {
                fobbidenList.add(userInfoENT.getUserId());
            }
        });
        if (fobbidenList.size() > 0) {
            fobbidenList.forEach(ent -> logger.info("[user_task] 防刷用户信息详情：{}", GfJsonUtil.toJSONString(ent)));
            userBaseInfoService.batchFobiddenLoginByUserId(fobbidenList, reason);
        }

    }

    //    =========================admin==================================
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public ExtModel listUserFilter(JSONObject jsonParam) {
        ExtModel result = new ExtModel();
        List<UserFilterENT> userFilterENTs = userFilterDao.listUserFilter(jsonParam.getInteger("page"),
                jsonParam.getInteger("limit"), jsonParam.getJSONObject("data"));

        result.setData(userFilterENTs);
        return result;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public ExtModel addUserFilter(UserFilterENT userFilter) {
        ExtModel result = new ExtModel();
        userFilter.setCreateTime(new Date());
        userFilter.setUpdateTime(new Date());
        userFilterDao.saveUserFilter(userFilter);
        return result;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public ExtModel updateUserFilter(UserFilterENT userFilter) {
        ExtModel result = new ExtModel();
        userFilter.setUpdateTime(new Date());
        userFilterDao.updateUserFilter(userFilter);
        return result;
    }

    private boolean validateEntranceCode(String entranceCodes1,String entranceCode){
        if(StringUtils.isNotBlank(entranceCodes1)) {
            String[] entranceCodeArray1 = entranceCodes1.split(",");
            if (Arrays.asList(entranceCodeArray1).contains(entranceCode)) {
                return true;
            }
        }
        return false;
    }
}
