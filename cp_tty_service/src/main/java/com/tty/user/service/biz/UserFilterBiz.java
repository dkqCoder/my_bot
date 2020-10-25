package com.tty.user.service.biz;

import com.google.common.collect.Lists;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.PropertiesUtil;
import com.tty.push.dto.PushUuidUserDTO;
import com.tty.push.outservice.PushOutService;
import com.tty.user.dao.entity.UserInfoENT;
import com.tty.user.service.UserBaseInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author chenpengfei {chenpengfei@jiangduoduo.com}
 * @Date 2017/11/13 15:54
 */
@Component("userFilterBiz")
public class UserFilterBiz {

    private static final Logger logger = LoggerFactory.getLogger(UserFilterBiz.class);
    /** 间隔时间范围 **/
    private final String FOBBIDEN_REASON = "注册检测防刷禁止登录";
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private PushOutService pushOutService;

    @Autowired
    private UserBaseInfoService userBaseInfoService;


    public void asyncFilterUserByRegister(UserInfoENT userInfoENT) {
        long invervalSecond = Long.valueOf(PropertiesUtil.get("forbbide.register.inveral"));
        threadPoolTaskExecutor.execute(() -> {

            try {
                logger.info("[user-api]防刷用户对象：{}", GfJsonUtil.toJSONString(userInfoENT));
                if (StringUtils.isNotBlank(userInfoENT.getUuid())) {
                    // 通过PUSH系统获取用户
                    PushUuidUserDTO pushUuidUserDTO = pushOutService.getPushUserByUuid(userInfoENT.getUuid());
                    if(pushUuidUserDTO == null) {
                        String pushUUIDEntrance = PropertiesUtil.get("user.pushuuid.entrance");
                        if(StringUtils.isNotBlank(pushUUIDEntrance)) {
                            String [] entranceArray = pushUUIDEntrance.split(",");
                            boolean isContain = Arrays.asList(entranceArray).contains(userInfoENT.getEntranceCode().toLowerCase());
                            if(isContain){
                                forbiddenLogin(userInfoENT);
                            }
                        }
                    }else {
                        Date pushCreateTime = pushUuidUserDTO.getCreateTime();
                        Date registerTime = userInfoENT.getRegisterTime();
                        long interval = (registerTime.getTime() - pushCreateTime.getTime()) / 1000;
                        logger.info("[user-api]注册防刷禁止登录 UserId:{},时间相差{}秒", userInfoENT.getUserId(), interval);
                        if (invervalSecond > interval) {
                            forbiddenLogin(userInfoENT);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("[user-api]注册防刷禁止登录 UserId{};StackTrace: {}", userInfoENT.getUserId(),
                        LogExceptionStackTrace.erroStackTrace(e));
            }
        });
    }
    private void forbiddenLogin(UserInfoENT userInfoENT) throws InterruptedException {
        logger.info("[user-api]注册防刷,满足条件 userId: {}", userInfoENT.getUserId());
        List<Long> fobbidenList = Lists.newLinkedList();
        fobbidenList.add(userInfoENT.getUserId());
        Thread.sleep(1000L);
        userBaseInfoService.batchFobiddenLoginByUserId(fobbidenList, FOBBIDEN_REASON);
    }

}
