package com.tty.user.service.handler.impl;/**
 * Created by shenwei on 2017/3/16.
 */

import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.tty.common.utils.Result;
import com.tty.user.common.utils.EntranceUtil;
import com.tty.user.controller.model.params.RegisterParams;
import com.tty.user.service.UserInfoService;
import com.tty.user.service.handler.RegisterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shenwei
 * @create 2017-03-16
 */
@Component("registerHandler")
public class RegisterHandlerImpl implements RegisterHandler {

    private static final Logger logger = LoggerFactory.getLogger(RegisterHandlerImpl.class);
    @Autowired
    private UserInfoService userInfoService;

    /**
     * @Author shenwei
     * @Date 2017/3/27 18:24
     * @Description 100 用户注册  增加 SmsType 如果传3(语音），不传默认1
     */
    @Override
    public Result register(String traceId, ClientRequestHeader header, String params) {
        RegisterParams registerParams = GfJsonUtil.parseObject(params, RegisterParams.class);
        if (registerParams == null) {
            logger.error("[100 用户注册] 参数错误  params:{} traceId:{}", params, traceId);
            return getFailResult();
        }
        if (null == registerParams.getSmsType() || registerParams.getSmsType().equals(0)) {
            registerParams.setSmsType(1);
        }
        if (EntranceUtil.forbiddenEntrance(header.getCmdName(), traceId) || EntranceUtil.forbiddenUUID(header.getPlatformCode(), header.getUuid(), traceId)) {
            Result result = new Result();
            result.setCode(Result.ERROR);
            result.setMsg("系统异常,请稍后再试,或联系客服");
            logger.warn("[渠道校验失败]header:{}", GfJsonUtil.toJSONString(header));
            return result;
        }
        String name = "";
        if (registerParams.getName() != null) {
            name = registerParams.getName().trim();
        } else {
            logger.warn("用户名为空 params:{}", params);
            Result result = new Result();
            result.setCode(Result.ERROR);
            result.setMsg("用户名不能为空 ");
            return result;
        }
        String passWord = registerParams.getPassWord();
        String registerType = registerParams.getRegistype();
        return userInfoService.register(name, Integer.valueOf(registerType), passWord, registerParams.getSmsType(), traceId, header);
    }

    private Result getFailResult() {
        Result result = new Result();
        result.setCode(Result.ERROR);
        result.setMsg(Result.MSG_ERROR_DESC);
        return result;
    }

}
