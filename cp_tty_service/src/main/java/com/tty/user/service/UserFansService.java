package com.tty.user.service;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.model.ExtModel;
import com.tty.common.utils.Result;
import com.tty.user.dao.entity.UserFansENT;
import com.tty.user.dto.AttentionDTO;
import com.tty.user.dto.AttentionInfoDTO;
import com.tty.user.dto.FansDTO;
import com.tty.user.dto.UserRelationDTO;

import java.util.List;

/**
 * @author zxh
 * @create 2017-03-06 18:03:22
 **/
public interface UserFansService {


    /**
     * 1341，获取用户的粉丝列表
     */
    FansDTO getUserFansList(Long queryUserId, Long userId, Integer page, Integer pageSize, String traceId);

    /**
     * 134，获取用户的关注列表
     */
    AttentionDTO getUserAttentionList(Long queryUserId, Long userId, Integer page, Integer pageSize, String traceId);

    /**
     * 135，用户关注(取消关注)大神
     */
    Result setUserFansRelation(Long userId, Long attentionUserId, Integer type, String traceId);

    AttentionInfoDTO getAttentionInfo(String traceId, Long userId, Long attentionUserId);

    /**
     * 用户的所有关注人ID
     *
     * @param traceId
     * @param userId
     * @return
     */
    List<Long> getAttentionUserIdAll(String traceId, Long userId);


    //    =========================admin==================================
    ExtModel listUserFans(JSONObject jsonParm, ExtModel result);

    void saveUserFans(UserFansENT UserFans);

    void updateUserFans(UserFansENT UserFans);

    UserRelationDTO getUserRelationInfo(Long queryUserId, Long userId, String traceId);


}
