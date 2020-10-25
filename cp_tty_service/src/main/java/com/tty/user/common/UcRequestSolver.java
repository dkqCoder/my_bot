package com.tty.user.common;

import com.jdd.fm.core.exception.AesException;
import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.TransferAesEncrypt;
import com.tty.user.controller.model.params.UserListParam;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by linian on 2017/10/20.
 */
public class UcRequestSolver {

    private static final Logger logger = LoggerFactory.getLogger(UcRequestSolver.class);

    public static UserListParam getUserListParam(HttpServletRequest request) {
        String jsonStr = getUcRequest(request);
        UserListParam userListParam = (UserListParam) GfJsonUtil.parseObject(jsonStr, UserListParam.class);
        return userListParam;
    }

    private static String getUcRequest(HttpServletRequest request){
        try {
            String e = IOUtils.toString(request.getInputStream());
            if(org.apache.commons.lang.StringUtils.isBlank(e)) {
                e = request.getParameter("request");
            }
            e = e.replace("request=", "");
            if(e.indexOf("%") >= 0) {
                e = URLDecoder.decode(e, "UTF-8");
            }
            String jsonStr = TransferAesEncrypt.aesDecrypt(e, "fAq70!F#36t^*t9y", "utf-8");
            return jsonStr;
        } catch (IOException var4) {
            logger.error("UcRequestSolver读取request异常{} ", LogExceptionStackTrace.erroStackTrace(var4));
        } catch (AesException var5) {
            logger.error("UcRequestSolver报文解密异常{} ", LogExceptionStackTrace.erroStackTrace(var5));
        }
        return null;
    }
}
