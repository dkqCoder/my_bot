package com.tty.user.dao;

import com.tty.user.dao.entity.UserFansENT;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author zxh
 * @create 2017-03-06 18:03:22
 **/
public interface UserFansDao {

    public void saveUserFans(UserFansENT ent);

    public void updateUserFans(UserFansENT ent);

    public void deleteUserFans(UserFansENT ent);

    public void saveOrUpdateUserFans(UserFansENT ent);
    
    List<UserFansENT> listUserFans(Integer page, Integer limit, JSONObject data);

    Long listUserFansCount(JSONObject data);

    /**
     * 查看我的关注
     */
    UserFansENT getUserFansRelation(Long userId, Long attentionUserId);

    /**
     * 获取用户粉丝id
     */
    List<String> getUserFans(Long userId);

    /**
     * 获取用户粉丝数量
     */
    Integer getUserFansCount(Long userId);

    /**
     * 获取用户粉丝id
     */
    List<Long> getUserFans(Long userId, Integer page, Integer pageSize);


    /**
     * 获取用户关注id
     */
    List<Long> getUserAttention(Long userId);

    /**
     * 获取用户关注数量
     */
    Integer getUserAttentionCount(Long userId);

    /**
     * 获取用户关注id
     */
    List<Long> getUserAttention(Long userId, Integer page, Integer pageSize);

    /**
     * 关注状态变化是刷新缓存
     */
    void setRedisUserAttentionstatus(Long userId, Long attentionUserId, Integer status);

    /**
     * 获取用户粉丝数
     *
     * @param userId
     * @return
     */
    Integer getUserFansCounts(Long userId);
}