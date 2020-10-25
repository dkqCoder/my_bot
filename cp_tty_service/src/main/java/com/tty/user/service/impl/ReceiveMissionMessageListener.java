package com.tty.user.service.impl;

import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.user.service.UserBusinessService;
import com.tty.user.dto.mission.UserMissionAddModel;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author chenlongfei
 * @date 2016-12-14
 * 消息中间件消费端
 */
@Service("receiveMissionMessageListener")
public class ReceiveMissionMessageListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(ReceiveMissionMessageListener.class);

    @Autowired
    private UserBusinessService userBusinessService;

    @Override
    public void onMessage(Message message) {
        String msgTxt = "";
        try {
            msgTxt = ((ActiveMQTextMessage) message).getText().toString();
            logger.info("消费到任务信息上报:msgTxt{}",msgTxt);
            if (!msgTxt.isEmpty()) {
                UserMissionAddModel um = GfJsonUtil.parseObject(msgTxt, UserMissionAddModel.class);
                //签到消费
                switch (um.getMissionType()) {
                    case 1:
                        userBusinessService.bindCertCardFilter(um);
                        break;
                    case 2:
                        userBusinessService.bindBankCardFilter(um);
                        break;
                    case 3:
                        userBusinessService.bindPhoneFilter(um);
                        break;
                    case 4:
                        userBusinessService.payFilter(um);
                        break;
                    case 5:
                        userBusinessService.followSchemeFilter(um);
                        break;
                    case 6:
                        try {
                            userBusinessService.buySchemeFilter(um);
                            userBusinessService.dailyBuyScheme(um);
                            userBusinessService.dailyBuySchemeGrowup(um);
                        } catch (Exception e) {
                            logger.error("每日购彩上报消费异常 【traceId={};UserId={};StackTrace:{};addModel={}】",
                                    um.getTraceId(), um.getUserId(), LogExceptionStackTrace.erroStackTrace(e), GfJsonUtil.toJSONString(um));
                        }
                        userBusinessService.checkForeverLoveDaysByWeek(um);
                        break;
                    case 7:
                        userBusinessService.signInFilter(um);
                        break;
                    case 8:
                        userBusinessService.dailyShowScheme(um);
                        break;
                    case 9:
                        userBusinessService.dailyShare(um);
                        break;
                    case 10:
                        userBusinessService.dailyComment(um);
                        break;
                    case 30:
                        userBusinessService.dailyLogin(um);
                        break;
                    default:break;
                }
            }
        } catch (Exception e) {
            logger.error("消费上报任务信息异常 【message={};StackTrace: {}】", GfJsonUtil.toJSONString(message), LogExceptionStackTrace.erroStackTrace(e));
        }
    }
}
