package com.tty.user.controller.base;


import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.tty.user.common.ExtModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description：基础 controller
 * @author：jdd
 * @date：2015/10/1 14:51
 */
public abstract class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String getTraceId(HttpServletRequest request) {
        String traceId = "";
        Object objTraceId = request.getAttribute("traceId");
        if (objTraceId != null) {
            traceId = objTraceId.toString();
        }
        return traceId;
    }

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ExtModel handlerThrowable(Throwable tr) {
        logger.error("{}", LogExceptionStackTrace.erroStackTrace(tr));
        ExtModel ex = new ExtModel();
        ex.setSuccess(false);
        ex.setData(tr.getMessage());
        return ex;
    }

}
