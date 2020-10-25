package com.tty.user.dao;

import com.alibaba.fastjson.JSONObject;
import com.tty.user.dao.entity.UserBaseInfoENT;
import com.tty.user.dao.entity.UserInfoENT;
import com.tty.user.dto.UserBaseInfoDTO;

import java.util.List;

/**
 * Created by donne on 17/03/07.
 */
public interface UserBaseInfoDao {

    void saveUserBaseInfo(UserBaseInfoENT ent);

    List<UserBaseInfoDTO> listUserBaseInfo(JSONObject searchCondition, Boolean all);

    Long getUserBaseInfoCount(JSONObject searchCondition);

    boolean updateIdcardnoAndRealName(Long userId, String idcardNumber, String realName);

    boolean nickNameAlreadyEdited(String userId);

    boolean nickNameAlreadyExists(String nickName);

    boolean editUserNickName(String userId, String nickName);

    UserBaseInfoENT getUserBaseInfo(String userId);

    void updateUserBaseInfo(UserBaseInfoENT userBaseInfo);

    List<UserBaseInfoENT> listUserBaseInfoByIdcardNumber(String idcardNumber);

    void updateUserBaseInfoByAdmin(Long userId, String realName, String idcardNumber);

    UserBaseInfoDTO getUserBaseInfoById(Long userId);

    List<UserInfoENT> getUserInfoByMobileAndUserId(String mobileNumber, Long userId);

    /**
     * 清空用户昵称和头像
     **/
    void resetUserNickNameAndHeadIcon(Long userId);

    /**
     * 清空用户头像
     **/
    void resetUserFace(Long userId);
    /**
     * 清空用户昵称
     **/
    void resetNickName(Long userId);
}
