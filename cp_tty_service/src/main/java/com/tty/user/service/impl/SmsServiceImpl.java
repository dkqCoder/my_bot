package com.tty.user.service.impl;/**
 * Created by shenwei on 2017/3/29.
 */

import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ResultModel;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.sms.dto.MessageDTO;
import com.tty.sms.outservice.SmsOutService;
import com.tty.user.common.utils.SmsUtil;
import com.tty.user.context.UserContext;
import com.tty.user.context.VerifyCodeEnum;
import com.tty.user.service.SmsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author shenwei
 * @create 2017-03-29
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
    @Autowired
    SmsOutService smsOutService;

    /**
     * @Author shenwei
     * @Date 2017/3/7 14:24
     * @Description 手机验证码派发
     */
    public Boolean sendVerifyCode(Integer siteId, String mobile, VerifyCodeEnum verifyCodeEnum, String userId, String verifyCode, Integer smsType, String traceId) {
        try {
            logger.info("验证码 mobile:{} userId:{} traceId:{} codeEnum:{},smsType:{}", mobile, userId, traceId, verifyCodeEnum.getName(), smsType);
            String messageBody = "";
            if (verifyCodeEnum == VerifyCodeEnum.LOGINFAST) {
                messageBody = String.format("您的手机注册验证码是:%s", verifyCode);
            } else {
                messageBody = String.format("您的验证码是:%s,请尽快完成验证。", verifyCode);
            }
            if (StringUtils.isBlank(messageBody)) {
                logger.error("验证码获取未识别的业务类型,mobile:{} userId:{} traceId:{}", mobile, userId, traceId);
                return false;
            }
            // 调用短信系统发送短信
            MessageDTO messageDTO = new MessageDTO(mobile, messageBody, smsType, "tty_user");
            boolean result = smsOutService.sendMsg(messageDTO);
            return result;
        } catch (Exception e) {
            logger.error("手机验证码短信发送异常 mobile:{} traceId:{} stackTrace:{}", mobile, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return false;
    }

    /**
     * @Author shenwei
     * @Date 2017/4/3 15:21
     * @Description wap注册发送随机密码短信
     */
    public Boolean sendWapRandomPass(Integer siteId, String mobile, String userId, String randomPass, String traceId) {
        try {
            String messageBody = String.format("您的初始密码是: %s ,为了您的账号安全,请尽快登录进行修改。下载天天盈彩票APP: %s", randomPass, UserContext.WAP_DOWNLOAD_URL);
            MessageDTO messageDTO = new MessageDTO(mobile, messageBody, 1, "tty_user");
            boolean result = smsOutService.sendMsg(messageDTO);
            return result;
        } catch (Exception e) {
            logger.error("手机初始密码短信发送异常 mobile:{} traceId:{} stackTrace:{}", mobile, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return false;
    }

    /**
     * @Author linian
     * @Date 2017/7/6 09:50
     * @Description 发送短信
     */
    public Boolean sendSMS(String mobile, String messageBody, String traceId) {
        try {

            MessageDTO messageDTO = new MessageDTO(mobile, messageBody, 1, "tty_user");
            boolean result = smsOutService.sendMsg(messageDTO);
            return result;
        } catch (Exception e) {
            logger.error("手机初始密码短信发送异常 mobile:{} traceId:{} stackTrace:{}", mobile, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return false;
    }

}
