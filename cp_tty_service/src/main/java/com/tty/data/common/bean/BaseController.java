package com.tty.data.common.bean;


import com.tty.user.common.ExtModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description：基础 controller
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
        tr.printStackTrace();
        ExtModel model = new ExtModel();
        model.setSuccess(false);
        model.setData(tr.getMessage());
        return model;
    }
}
