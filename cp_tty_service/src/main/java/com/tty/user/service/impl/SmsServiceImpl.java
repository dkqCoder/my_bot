package com.tty.user.service.impl;/**
 * Created by shenwei on 2017/3/29.
 */

import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ResultModel;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.common.utils.SmsUtil;
import com.tty.user.context.UserContext;
import com.tty.user.context.VerifyCodeEnum;
import com.tty.user.dto.SmsDTO;
import com.tty.user.service.SmsSendMsgService;
import com.tty.user.service.SmsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shenwei
 * @create 2017-03-29
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    /*@Autowired
    private SmsSendMsgService smsSendMsgService;*/


    @Override
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
            SmsDTO smsDTO = new SmsDTO();
            smsDTO.setMobileList(mobile);
            smsDTO.setBody(messageBody);
            smsDTO.setSMSType(smsType);
            smsDTO.setSystemFrom("jdd");
            String resultStr = SmsUtil.sendPost(UserContext.SMS_URL, GfJsonUtil.toJSONString(smsDTO));
            logger.info("调用短信发送,收到返回结果 {}", resultStr);
            if (StringUtils.isBlank(resultStr)) {
                return false;
            }
            ResultModel resultModel = GfJsonUtil.parseObject(resultStr, ResultModel.class);
            return resultModel.getCode() >= 0;
        } catch (Exception e) {
            logger.error("手机验证码短信发送异常 mobile:{} traceId:{} stackTrace:{}", mobile, traceId, LogExceptionStackTrace.erroStackTrace(e));
        }
        return false;
    }
}
