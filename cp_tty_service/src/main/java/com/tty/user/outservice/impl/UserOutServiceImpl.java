package com.tty.user.outservice.impl;

import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.tty.user.dao.entity.UserInfoENT;
import com.tty.user.dto.UserBaseInfoDTO;
import com.tty.user.dto.UserBaseInfoForApiDTO;
import com.tty.user.dto.UserInfoDTO;
import com.tty.user.outservice.UserOutService;
import com.tty.user.service.UserBaseInfoService;
import com.tty.user.service.UserInfoService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: sunyishun
 * @Date: 2020/10/19 8:56 下午
 * @Description: 用户系统对外接口
 */
public class UserOutServiceImpl implements UserOutService {

    private static final Logger logger = LoggerFactory.getLogger(UserOutServiceImpl.class);

    @Autowired
    private UserBaseInfoService userBaseInfoService;
    @Autowired
    private UserInfoService userInfoService;

    public List<UserBaseInfoDTO> listUserBaseInfo(String traceId, Long[] userIds, boolean isGetUserLevel, boolean isGetUserInteral, boolean isGetUserSignInfo) throws Exception {
        try {
            if (userIds == null || (userIds != null && userIds.length < 1)) {
                return null;
            }
            List<UserBaseInfoDTO> list = new ArrayList<UserBaseInfoDTO>();
            List<UserBaseInfoForApiDTO> userBaseInfoForApiDTOs = userBaseInfoService.listUserBaseInfo(traceId, userIds, isGetUserLevel, isGetUserInteral, isGetUserSignInfo);
            if (CollectionUtils.isEmpty(userBaseInfoForApiDTOs)) {
                return null;
            }
            for (UserBaseInfoForApiDTO ud : userBaseInfoForApiDTOs) {
                UserBaseInfoDTO userBaseInfoDTO = new UserBaseInfoDTO();
                BeanUtils.copyProperties(userBaseInfoDTO, ud);
                list.add(userBaseInfoDTO);
            }
            return list;

        } catch (Exception e) {
            logger.error("listUserBaseInfo:traceId={},userIds={},isGetUserLevel={},isGetUserInteral={},isGetUserSignInfo={}，{}",
                    traceId, userIds, isGetUserLevel, isGetUserInteral, isGetUserSignInfo, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }


    public UserBaseInfoDTO getUserBaseInfo(String traceId, Long userId, boolean isGetUserLevel, boolean isGetUserInteral, boolean isGetUserSignInfo) throws Exception {
        try {
            if (userId == null) {
                return null;
            }
            List<UserBaseInfoForApiDTO> userBaseInfoForApiDTOs = userBaseInfoService.listUserBaseInfo(traceId, new Long[]{userId}, isGetUserLevel, isGetUserInteral, isGetUserSignInfo);
            if (CollectionUtils.isEmpty(userBaseInfoForApiDTOs)) {
                return null;
            }
            for (UserBaseInfoForApiDTO ud : userBaseInfoForApiDTOs) {
                UserBaseInfoDTO userBaseInfoDTO = new UserBaseInfoDTO();
                BeanUtils.copyProperties(userBaseInfoDTO, ud);
                return userBaseInfoDTO;
            }
        } catch (Exception e) {
            logger.error("getUserBaseInfo:traceId={},userId={},isGetUserLevel={},isGetUserInteral={},isGetUserSignInfo={}，{}",
                    traceId, userId, isGetUserLevel, isGetUserInteral, isGetUserSignInfo, LogExceptionStackTrace.erroStackTrace(e));
        }
        return null;
    }



    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public UserInfoDTO getUserBaseInfo(String traceId, String userName) throws Exception {
        UserInfoENT userInfoENT = userBaseInfoService.getUserBaseInfoByName(traceId, userName);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfoDTO, userInfoENT);
        return userInfoDTO;
    }

    public UserInfoDTO getUserInfo(String traceId, Long userId) throws Exception {
        UserInfoENT userInfoENT = userInfoService.getCurrentUserInfo(String.valueOf(userId));
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfoDTO, userInfoENT);
        return userInfoDTO;
    }
}
