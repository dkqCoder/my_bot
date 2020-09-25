package com.tty.user.service.impl;/**
 * Created by shenwei on 2017/3/16.
 */

import com.jdd.fm.core.model.ClientRequestHeader;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.StringUtils;
import com.tty.common.utils.Result;
import com.tty.user.params.RegisterParams;
import com.tty.user.service.RegisterHandler;
import com.tty.user.service.UserInfoService;
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

    @Autowired
    private UserInfoService userInfoService;

    private static final Logger logger = LoggerFactory.getLogger(RegisterHandlerImpl.class);

    public Result register(String traceId, ClientRequestHeader header, String params) {
        RegisterParams registerParams = GfJsonUtil.parseObject(params, RegisterParams.class);
        if (registerParams == null) {
            logger.error("[100 用户注册] 参数错误  params:{} traceId:{}", params, traceId);
            return getFailResult();
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
