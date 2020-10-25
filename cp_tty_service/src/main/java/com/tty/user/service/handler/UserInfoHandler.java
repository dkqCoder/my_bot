package com.tty.user.service.handler;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.Result;
import org.springframework.web.multipart.MultipartFile;

public interface UserInfoHandler {

    Result getBindMobileVerifyCode(String traceId, String userID, String params);

    Result bindUserMobile(String traceId, String userID, String params, ClientRequestHeader header);

    Result bindUserRealityNameAndCertCard(String traceId, String userID, String params, ClientRequestHeader header);

    Result changeUserPassword(String traceId, String userID, String params);

    Result changeUserPayPassword(String traceId, String userID, String params);

    Result resetPassword(String traceId, String params, ClientRequestHeader header);

    Result addUserFacePic(String traceId, MultipartFile file, String bfile, String fileName, String userId);

    Result getForgetPassVerifyCode(String traceId, String params);

    Result getLoginPassVerifyCode(String traceId, String params);

    Result checkForgetPassVerifyCode(String traceId, String params);

    Result getForgetPayPassVerifyCode(String traceId, String userID, String params);

    Result checkForgetPayPassVerifyCode(String traceId, String userID, String params);

    Result getQuickRegisterVerifyCode(String traceId, String params);

    Result checkQuickRegisterVerifyCode(String traceId, ClientRequestHeader header, String params);

    Result getWapVerifyCode(String traceId, String params);

    Result checkWapVerifyCode(String traceId, ClientRequestHeader header, String params);

    Result getUserBaseInfo(String traceId, String userId, String isGetUserLevel, String isGetUserInteral, String isGetUserSignInfo);

    Result thdPartChangeUserPassword(String traceId, String userID, String params);

    Result getThdPartBindUserMobileVerifyCode(String traceId, String userID, String params);

    Result resetIdCardNumber(String traceId, String userId, String params, MultipartFile[] file, String[] bfile, String[] fileName);

    Result getResetIdCardInfo(String traceId, String userId);

    Result getUserAttentionList(String traceId, String userId, String params);

    Result getUserFansList(String traceId, String userId, String params);

    Result setUserFansRelation(String traceId, String userId, String params);

    Result userLogout(String traceId, String userId,String token);

    Result checkLoginByMobile(String traceId, String params);

    Result searchMasterInfoByNickName(String traceId, String userId, String params);

    Result bindUserPayPassword(String traceId, String userId, String params);

    Result editNickName(String traceId, String userId, String params);

    Result checkPCQuickRegister(String traceId, ClientRequestHeader clientRequestHeader, String params);

    Result getUserInfoByUserName(String traceId, String userName);

    Result getUserInfoByNameOrMobile(String traceId, String mobile);

    Result getBasicUserInfo(String traceId, ClientRequestHeader clientRequestHeader, String params,String userId);
}
