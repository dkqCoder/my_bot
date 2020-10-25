package com.tty.user.service.impl;

import com.jdd.fm.core.redis.JedisClusterFactory;
import com.tty.user.context.UserRedisKeys;
import com.tty.user.dao.entity.UserInfoENT;
import com.tty.user.dao.impl.UserInfoDaoImpl;
import com.tty.user.service.ShardedRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 用于操作Javaredis数据库
 *
 * @Author wenxiaoqing
 * @Date 2017/4/4
 * @Description
 */
@Component
public class ShardedRedisServiceImpl implements ShardedRedisService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoDaoImpl.class);
    @Autowired
    @Qualifier("userRedis")
    private JedisClusterFactory userRedis;

    /**
     * 删除redis中用户基本信息
     *
     * @param userInfoENT
     */
    @Override
    public void delUserInfo(UserInfoENT userInfoENT) {
        userRedis.del(String.format(UserRedisKeys.USER_INFO_KEY_ID, userInfoENT.getUserId().toString()));
        userRedis.del(String.format(UserRedisKeys.USER_BASE_INFO_KEY_ID, userInfoENT.getUserId().toString()));
        if (userInfoENT.getMobileNumber() != null) {
            userRedis.del(String.format(UserRedisKeys.USRE_INFO_MOBILE_LIST_NUMBER, userInfoENT.getMobileNumber()));
        }
        if (userInfoENT.getLoginName() != null) {
            userRedis.del(String.format(UserRedisKeys.USER_INFO_NAME_KEY_NAME, userInfoENT.getLoginName()));
        }
    }
}
