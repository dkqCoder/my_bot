package com.tty.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.model.ExtModel;
import com.jdd.fm.core.utils.DateUtil;
import com.tty.common.utils.Result;
import com.tty.user.common.utils.MobileUtil;
import com.tty.user.common.utils.PublicCommonRedisUtil;
import com.tty.user.context.UserContext;
import com.tty.user.context.UserInfoExtensionFields;
import com.tty.user.dao.UserFansDao;
import com.tty.user.dao.entity.UserBaseInfoENT;
import com.tty.user.dao.entity.UserFansENT;
import com.tty.user.dto.*;
import com.tty.user.service.UserFansService;
import com.tty.user.service.UserInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("userFansService")
public class UserFansServiceImpl implements UserFansService {
    private static final Logger logger = LoggerFactory.getLogger(UserBaseInfoServiceImpl.class);
    @Autowired
    private UserFansDao userFansDao;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private PublicCommonRedisUtil publicCommonRedisUtil;

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public FansDTO getUserFansList(Long queryUserId, Long userId, Integer page, Integer pageSize, String traceId) {
        FansDTO dto = new FansDTO();
        List<Long> fansIds = userFansDao.getUserFans(queryUserId, page, pageSize);
        List<Long> myAttention = null;
        if (userId != null) {
            myAttention = userFansDao.getUserAttention(userId);
        }
        List<UserFansDTO> list = new ArrayList<>();
        UserFansDTO fansDTO = null;
        for (Long fansId : fansIds) {
            fansDTO = new UserFansDTO();
            fansDTO.setFansUserID(fansId);
            //如果用户没有登录都是未关注
            if (myAttention == null) {
                fansDTO.setIsAttention(false);
            } else {
                //用户登录后判断有没有在关注列表
                if (myAttention.contains(fansId)) {
                    fansDTO.setIsAttention(true);
                } else {
                    fansDTO.setIsAttention(false);
                }
            }
            //用户头像和昵称
            UserBaseInfoENT userBase = userInfoService.getCurrentUserBaseInfo(String.valueOf(fansId));
            if (userBase != null) {
                fansDTO.setFansName(getUserValidName(userBase));
                fansDTO.setUserFace(userBase.getFaceUrl());
            }
            list.add(fansDTO);
        }
        dto.setDetail(list);
        dto.setTotal(userFansDao.getUserFansCount(queryUserId));
        return dto;
    }

    /**
     * 昵称为空是 显示登录名 登录名为手机号要打码
     */
    private String getUserValidName(UserBaseInfoENT userBase) {
        String name = "";
        //昵称为空是 显示登录名 登录名为手机号要打码
        if (StringUtils.isEmpty(userBase.getNickName())) {
            if (StringUtils.isNotEmpty(userBase.getName())) {
                if (MobileUtil.isMobileNO(userBase.getName())) {
                    name = MobileUtil.getSaveMobileNO(userBase.getName());
                } else {
                    name = userBase.getName();
                }
            }
        } else {
            name = userBase.getNickName();
        }
        return name;
    }

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public AttentionDTO getUserAttentionList(Long queryUserId, Long userId, Integer page, Integer pageSize, String traceId) {
        AttentionDTO attentionDto = new AttentionDTO();
        List<Long> fansIds = userFansDao.getUserAttention(queryUserId, page, pageSize);
        List<Long> myAttention = null;
        if (userId != null) {
            myAttention = userFansDao.getUserAttention(userId);
        }
        List<UserAttentionInfoDTO> list = new ArrayList<>();
        UserAttentionInfoDTO dto = null;
        for (Long uid : fansIds) {
            dto = new UserAttentionInfoDTO();
            dto.setUserID(uid);
            //我的关注不存在返回为未关注
            if (myAttention == null) {
                dto.setIsAttention(false);
            } else {
                //存在在有没有在我的关注列表
                if (myAttention.contains(uid)) {
                    dto.setIsAttention(true);
                } else {
                    dto.setIsAttention(false);
                }
            }
            UserBaseInfoENT userBase = userInfoService.getCurrentUserBaseInfo(String.valueOf(uid));
            if (userBase != null) {
                dto.setUserName(getUserValidName(userBase));
                dto.setUserFace(userBase.getFaceUrl());
            }
            String userLevel = publicCommonRedisUtil.getUserInfoExtension(userId, UserInfoExtensionFields.USER_LEVEL);
            if (userLevel == null) {
                userLevel = "0";
            }
            String levelName = UserContext.USER_LEVEL_NAME_MAP.get(Integer.parseInt(userLevel));
            dto.setUserLevel(userLevel);
            dto.setLevelName(levelName);
            dto.setDateTime(DateUtil.format(new Date()));
            list.add(dto);
        }
        attentionDto.setDetail(list);
        attentionDto.setTotal(userFansDao.getUserAttentionCount(queryUserId));
        return attentionDto;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public Result setUserFansRelation(Long userId, Long attentionUserId, Integer type, String traceId) {
        Result result = new Result();
        if (attentionUserId == null) {
            result.setCode(Result.ERROR);
            result.setMsg("参数有误");
            return result;
        }
        if (userId == attentionUserId.longValue()) {
            result.setCode(Result.ERROR);
            result.setMsg("不能关注自己");
            return result;
        }
        UserFansENT ent = userFansDao.getUserFansRelation(userId, attentionUserId);
        if (ent == null) {
            ent = new UserFansENT();
            Date date = DateUtil.nowDate();
            ent.setUserId(userId);
            ent.setAttentionUserId(attentionUserId);
            ent.setStatus(type);
            ent.setCreateTime(date);
            ent.setUpdateTime(date);
            userFansDao.saveUserFans(ent);
            logger.info("用户关注/取消关注成功 userId:{},attentionUserId:{},type:{},traceId:{}", userId, attentionUserId, type, traceId);
        } else {
            ent.setStatus(type);
            ent.setUpdateTime(DateUtil.nowDate());
            userFansDao.updateUserFans(ent);
            logger.info("用户关注/取消关注成功 userId:{},attentionUserId:{},type:{},traceId:{}", userId, attentionUserId, type, traceId);
        }
        userFansDao.setRedisUserAttentionstatus(userId, attentionUserId, type);
        //粉丝数量
        Integer fansCount = userFansDao.getUserFansCount(attentionUserId);
        //关注数量
        Integer attentionCount = userFansDao.getUserAttentionCount(attentionUserId);
        UserAttentionDTO dto = new UserAttentionDTO();
        dto.setAttentionCount(attentionCount);
        dto.setFansCount(fansCount);
        boolean isAttention = type == UserContext.USER_FANS_STATUS_ATTENTION;
        dto.setIsAttention(isAttention);
        dto.setShowFirst(0);
        result.setData(dto);
        return result;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public AttentionInfoDTO getAttentionInfo(String traceId, Long userId, Long attentionUserId) {
        List<Long> userAttentions = userFansDao.getUserAttention(userId);
        AttentionInfoDTO dto = new AttentionInfoDTO();
        if (CollectionUtils.isEmpty(userAttentions)) {
            dto.setIsAttention(false);
        }
        dto.setIsAttention(userAttentions.contains(attentionUserId));
        return dto;
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public List<Long> getAttentionUserIdAll(String traceId, Long userId) {
        return userFansDao.getUserAttention(userId);
    }

    //    =========================admin==================================
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public ExtModel listUserFans(JSONObject jsonParm, ExtModel result) {
        result.setData(userFansDao.listUserFans(jsonParm.getInteger("page"), jsonParm.getInteger("limit"), jsonParm.getJSONObject("data")));
        result.setTotal(userFansDao.listUserFansCount(jsonParm.getJSONObject("data")));
        return result;

    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void saveUserFans(UserFansENT userFans) {
        userFansDao.saveUserFans(userFans);
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public void updateUserFans(UserFansENT userFans) {
        userFansDao.updateUserFans(userFans);
    }


    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public UserRelationDTO getUserRelationInfo(Long queryUserId, Long userId, String traceId) {
        Integer fansCount = userFansDao.getUserFansCount(queryUserId);
        Integer attentionCount = userFansDao.getUserAttentionCount(queryUserId);
        List<Long> userAttentions = userFansDao.getUserAttention(userId);

        UserRelationDTO dto = new UserRelationDTO();
        dto.setAttentionCount(attentionCount);
        dto.setFansCount(fansCount);
        if (CollectionUtils.isEmpty(userAttentions)) {
            dto.setIsAttention(false);
        } else {
            dto.setIsAttention(userAttentions.contains(queryUserId));
        }
        UserBaseInfoENT userBase = userInfoService.getCurrentUserBaseInfo(queryUserId.toString());
        if (userBase != null) {
            dto.setAttuseFace(userBase.getFaceUrl());
        }
        String userLevel = publicCommonRedisUtil.getUserInfoExtension(queryUserId, UserInfoExtensionFields.USER_LEVEL);
        if (userLevel == null) {
            userLevel = "0";
        }
        String levelName = UserContext.USER_LEVEL_NAME_MAP.get(Integer.parseInt(userLevel));
        dto.setUserLevel(userLevel);
        dto.setLevelName(levelName);
        return dto;
    }
}
