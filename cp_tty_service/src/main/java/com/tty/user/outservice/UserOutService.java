package com.tty.user.outservice;

import com.tty.user.dto.UserBaseInfoDTO;
import com.tty.user.dto.UserInfoDTO;

import java.util.List;

/**
 * @Author: sunyishun
 * @Date: 2020/10/19 8:55 下午
 * @Description: 用户系统对外接口
 */
public interface UserOutService {
    List<UserBaseInfoDTO> listUserBaseInfo(String traceId, Long[] userIds, boolean isGetUserLevel, boolean isGetUserInteral, boolean isGetUserSignInfo) throws Exception;

    UserBaseInfoDTO getUserBaseInfo(String traceId, Long userId, boolean isGetUserLevel, boolean isGetUserInteral, boolean isGetUserSignInfo) throws Exception;

    UserInfoDTO getUserBaseInfo(String traceId, String userName) throws Exception;

    UserInfoDTO getUserInfo(String traceId, Long userId) throws Exception;


}
