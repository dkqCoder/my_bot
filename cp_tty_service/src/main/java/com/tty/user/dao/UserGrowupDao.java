package com.tty.user.dao;

import com.tty.user.dao.pojo.UserGrowupRecord;
import com.tty.user.dao.pojo.UserLevelAuthority;
import com.tty.user.dao.pojo.UserLevelMapping;

import java.util.List;
import java.util.Map;

/**
 * Created by shenwei on 2017/1/4.
 */
public interface UserGrowupDao {

    //获取用户等级成长值
    List<Map<String, Object>> getUserLevelAndGrowup(Long userId);

    //用户成长值变动
    Boolean changeUserGrowup(Long userId, Long growUps, Integer level, String description, Boolean levelChanged);

    //用户成长值明细获取
    List<UserGrowupRecord> getUserGrowupRecord(Long userId, Integer pageIndex, Integer pageSize);

    //获取等级成长值映射
    List<UserLevelMapping> getUserLevelMapping();

    //获取等级对应特权
    List<UserLevelAuthority> getLevelAuthority(Integer level);

    //获取用户等级击败用户比例
    String getUserLevelBeat(Integer level);

    //同步用户名信息
    Boolean syncUserBaseInfo(Long userId, String userName);
    
    //是否已领升级礼包
    Boolean IsAlreadyGetGift(Long userId, Integer giftId);

    //获取礼包匹配等级种类等信息
    List<Map<String, Object>> getGiftMappingLevel(Integer giftId);

    //存入用户领取礼包记录
    void saveUserGiftRecord(Long userId, Integer giftId);

    void removeGrowupRecords();
}
