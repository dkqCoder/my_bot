package com.tty.user.service;

import com.tty.user.dao.pojo.UserLevelAuthority;
import com.tty.user.model.result.UserGrowupModel;
import com.tty.user.model.result.UserGrowupRecordModel;
import com.tty.user.model.result.UserLevelsModel;

import java.util.List;

/**
 * Created by shenwei on 2017/1/4.
 */
public interface UserGrowupService {

    //获取用户等级成长值
    UserGrowupModel getUserLevelAndGrowups(String traceId, Long userId);

    //获取用户等级
    UserGrowupModel getUserLevelByUserId(String traceId, Long userId);

    //批量获取用户等级
    UserLevelsModel getUserLevels(String traceId, Long[] userIds);

    //用户成长值变动
    Boolean changeUserGrowup(String traceId, Long userId, Long growups, String description);

    //用户成长值明细获取
    List<UserGrowupRecordModel> getUserGrowupRecords(String traceId, Long userId, Integer pageIndex, Integer pageSize);

    //查找成长值对应等级
    Integer findLevelByGrowup(Long growups);

    //获取升级解锁特权
    List<UserLevelAuthority> getUnlockAuthority(Integer levelNow, Integer levelUpper);

    //获取用户会员中心首页
    Object getUserLevelCenter(String traceId, Long userId);

    //用户领取礼包
    Boolean getLevelupGift(String traceId, Integer giftId, Long userId);

    void removeGrowupRecords();

}
