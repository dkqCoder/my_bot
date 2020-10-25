package com.tty.user.service;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.model.ExtModel;
import com.tty.common.utils.Result;
import com.tty.user.dao.entity.UserBaseInfoENT;
import com.tty.user.dao.entity.UserInfoENT;
import com.tty.user.dto.UserBaseInfoDTO;
import com.tty.user.dto.UserBaseInfoForApiDTO;

import java.util.List;


/**
 * Created by donne on 17/03/07.
 */
public interface UserBaseInfoService {
    ExtModel listUserBaseInfo(JSONObject jsonParam);

    ExtModel listUserBaseInfoAll(JSONObject jsonParm);

    ExtModel updateUserBaseInfoStatus(Long userId, int status, String reason);

    ExtModel updateUserBaseInfoByAdmin(Long userId, String realName, String mobileNumber, String idcardNumber);

    /**
     * @Author shenwei
     * @Date 2017/3/13 16:55
     * @Description 102 绑定用户真实姓名和身份证
     */
    Result bindUserRealityNameAndCertCard(String userId, String realityName, String certCard, String traceId, ClientRequestHeader header);

    Result bindUserRealityNameAndCertCardForAdmin(String userId, String realityName, String certCard, String traceId);

    /**
     * @Author shenwei
     * @Date 2017/3/14 16:39
     * @Description 修改昵称
     */
    Result editUserNickName(String userId, String nickName, String traceId);

    void updateUserBaseInfo(UserBaseInfoENT userBaseInfo);

    List<UserBaseInfoForApiDTO> listUserBaseInfo(String traceId, Long[] userIds, boolean isGetUserLevel, boolean isGetUserInteral, boolean isGetUserSignInfo);

    void getUserStopReasonList(ExtModel em, JSONObject jsonObject);

    String resetUserPasswd(Long userId);

    void resetUserPayPasswd(Long userId);

    /**
     * 重置用户昵称和头像地址
     **/
    void resetUserNickNameAndHeadIcon(Long userId);

    UserBaseInfoDTO getUserBaseInfoById(Long userId);

    UserInfoENT getUserBaseInfoByName(String traceId, String userName);

    Result getBasicUserInfo(String traceId, ClientRequestHeader header, String userID, String params);

    /**
     * 批量禁止登录
     */
    void batchFobiddenLogin(List<Long> userIds,Integer status);

    void batchFobiddenLogin(List<Long> userIds,Integer status, String reason);

    void batchFobiddenLoginByMobile(List<String> mobiles, String reason);

    Result userRedisExamine(String traceId, String userID, String loginName, String type, String operate);

    void resetUserFace(Long userId);

    void resetUserNickName(Long userId);

    void batchFobiddenLoginByUserId(List<Long> userId, String reason);

}
