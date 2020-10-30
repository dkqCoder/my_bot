package com.tty.data.common.util;

import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.utils.PropertiesUtil;
import com.tty.data.common.bean.EmailModel;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by donne on 2017/3/16.
 */
public class EmailUtil {
    private static Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    private static final String SMTP_HOST = PropertiesUtil.get("smtp.host");
    private static final String SMTP_USERNAME = PropertiesUtil.get("smtp.username");
    private static final String SMTP_PASSWORD = PropertiesUtil.get("smtp.password");

    public static void sendEmail(EmailModel mail) {
        try {
            HtmlEmail email = new HtmlEmail();
            email.setHostName(SMTP_HOST);
            email.setAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
            if(mail.getToEmail()!=null && mail.getToEmail().isEmpty()==false) {
                for(String ma : mail.getToEmail()) {
                    email.addTo(ma);
                }
            }
            email.setFrom(mail.getFromEmail());
            email.setCharset("UTF-8");
            email.setSubject(mail.getSubject());
            email.setHtmlMsg(mail.getText());
            email.send();
        } catch (Exception e) {
            logger.error("发送邮件异常!! StackTrace = {}", LogExceptionStackTrace.erroStackTrace(e));
            e.printStackTrace();
        }
    }
}
