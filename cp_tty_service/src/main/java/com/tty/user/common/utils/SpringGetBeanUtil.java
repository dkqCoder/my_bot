package com.tty.user.common.utils;

import com.jdd.fm.core.context.BaseContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Created by jdd on 2016/9/14.
 */
public class SpringGetBeanUtil {
    public static Object getBean(String beanName) {
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(BaseContext.sc);
        return wac.getBean(beanName);
    }
}
