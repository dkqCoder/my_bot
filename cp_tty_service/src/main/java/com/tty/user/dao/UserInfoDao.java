package com.tty.user.dao;

import com.alibaba.fastjson.JSONObject;
import com.tty.user.dao.entity.UserInfoENT;
import com.tty.user.dto.RegisterCountDTO;
import com.tty.user.dto.UserMobileDTO;

import java.util.Date;
import java.util.List;

/**
 * @author zxh
 * @create 2017-03-06 18:02:49
 **/
public interface UserInfoDao {

    public void saveUserInfo(UserInfoENT ent);

    public void updateUserInfo(UserInfoENT ent);

    public void deleteUserInfo(UserInfoENT ent);

    public void saveOrUpdateUserInfo(UserInfoENT ent);

    List<UserInfoENT> listUserInfo(Integer page, Integer limit, JSONObject data);

    Long listUserInfoCount(JSONObject data);

    Boolean updateUserMobileNumberAndPassword(String userId, String mobileNumber, String password);

    Boolean updateUserMobileNumber(String userId, String mobileNumber);

    Boolean updateUserPass(String userId, String password);

    Boolean updateUserPayPass(String userId, String payPassword);

    Boolean updateUserWeChat(String userId, String thdUserId, String thdUserName, String thdType);

    UserInfoENT getUserInfo(String userId);

    UserInfoENT getUserInfoByLoginName(String loginName);

    UserInfoENT getUserInfoByThdId(String thdId);

    UserInfoENT getUserInfoByThdId(String thdId, String thdType);

    List<UserInfoENT> getUserInfoByMobile(String mobile);

    List<UserInfoENT> getUserThirdInfo();

    /**
     * 清空用户缓存
     */
    void emptyUserInfoRedisCache(Long userId);

    /**
     * 清空net用户缓存
     */
    void netEmptyUserInfoRedisCache(Long userId);

    void resetUserPasswd(Long userId, String randowmPasswd);

    void resetUserPayPasswd(Long userId);

    void updateUserInfoByAdmin(Long userId, String mobileNumber);

    void updateUserLastLoginTime(String userId, Date date);

    List<UserInfoENT> getUserInfoByMobileNumber(Long userId, Integer size);

    void updateUserInfoStatus(Long userId, int status, String reason);

    Boolean updateUserWxInfoSetEmpty(Long userId);

    List<RegisterCountDTO> listRegisterCount(JSONObject searchCondition);

    List<UserInfoENT> listUserInfoByUuid(String uuid);

    List<UserInfoENT> listUserInfoByUuidAndCmdName(String uuid, String cmdName);

    List<UserMobileDTO> getMobilesLastOneHour();

    List<UserMobileDTO> getMobilesBetween(Long minUserId, Long maxUserId);

    List<UserInfoENT> getUserInfoByDate(Integer queryHour);

    Integer getUuidsByDate(Date date, String uuid);

    Integer getIpsByDate(Date date, String ip);
}