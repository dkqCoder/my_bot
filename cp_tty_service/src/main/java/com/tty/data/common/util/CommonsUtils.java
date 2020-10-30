package com.tty.data.common.util;

import com.jdd.fm.core.utils.PropertiesUtil;
import com.tty.data.common.bean.EmailModel;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author chenlf
 *         公共工具类
 */
public class CommonsUtils {
    public static String getTraceId(HttpServletRequest request) {
        String traceId = "";
        Object obj = request.getAttribute("traceId");
        if (obj != null) {
            traceId = obj.toString();
        }
        return traceId;
    }


    /**
     * @Author shenwei
     * @Date 2017/2/21 19:01
     * @Description 同名属性拷贝 from拷贝给to
     */
    public static void propertyCopy(Object from, Object to, String[] excludsArray) throws Exception {
        List<String> excludesList = null;
        if (excludsArray != null && excludsArray.length > 0) {
            excludesList = Arrays.asList(excludsArray); // 构造列表对象
        }
        Method[] fromMethods = from.getClass().getDeclaredMethods();
        Method[] toMethods = to.getClass().getDeclaredMethods();
        Method fromMethod = null, toMethod = null;
        String fromMethodName = null, toMethodName = null;
        for (int i = 0; i < fromMethods.length; i++) {
            fromMethod = fromMethods[i];
            fromMethodName = fromMethod.getName();
            if (!fromMethodName.contains("get"))
                continue;
            // 排除列表检测
            if (excludesList != null
                    && excludesList.contains(fromMethodName.substring(3)
                    .toLowerCase())) {
                continue;
            }
            toMethodName = "set" + fromMethodName.substring(3);
            toMethod = findMethodByName(toMethods, toMethodName);
            if (toMethod == null)
                continue;
            Object value = fromMethod.invoke(from, new Object[0]);
            if (value == null)
                continue;
            // 集合类判空处理
            if (value instanceof Collection) {
                Collection<?> newValue = (Collection<?>) value;
                if (newValue.size() <= 0)
                    continue;
            }
            toMethod.invoke(to, new Object[]{value});
        }
    }

    public static Method findMethodByName(Method[] methods, String name) {

        for (int j = 0; j < methods.length; j++) {

            if (methods[j].getName().equals(name)) {

                return methods[j];
            }

        }
        return null;
    }

    /**
     * 发送邮件给交易组
     *
     * @param subject
     * @param html
     */
    public static void sendEmailToJY(String subject, String html) {
        EmailModel em = new EmailModel();
        em.setFromEmail(PropertiesUtil.get("smtp.fromemail"));
        List<String> toEms = Arrays.asList(PropertiesUtil.get("smtp.toemail").split(","));
        em.setToEmail(toEms);
        em.setSubject(subject);
        em.setText(html);
        EmailUtil.sendEmail(em);
    }

    public static boolean validateTicketSystemToken(String token, String msg) {
        String pwd = PropertiesUtil.get("ticket.password");
        String md5Value = DigestUtils.md5Hex(msg + pwd).toLowerCase();
        return md5Value.equals(token);
    }

    public static int convertNum(Object number) {
        if (number == null) {
            return 0;
        }
        return Integer.valueOf(number.toString());
    }

}
