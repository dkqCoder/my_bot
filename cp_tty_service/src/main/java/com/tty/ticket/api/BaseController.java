package com.tty.ticket.api;


import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.model.ExtModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description：基础 controller
 * @author：jdd
 * @date：2015/10/1 14:51
 */
public abstract class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

        /**
         * 防止XSS攻击
         */
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Object handlerThrowable(Throwable tr) {
        logger.error("后台异常：{}", LogExceptionStackTrace.erroStackTrace(tr));
        tr.printStackTrace();
        ExtModel model = new ExtModel();
        model.setSuccess(false);
        model.setData(tr.getMessage());
        return model;
    }
}
