package com.tty.common.utils;

import com.tty.user.context.UserContext;
import org.apache.commons.lang.StringUtils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ln
 */
public class MobileUtil {

    public static String getSaveMobileNO(String userName) {
        if (StringUtils.isNotBlank(userName) && isMobileNO(userName)) {
            if (userName.length() >= 4) {
                userName = userName.substring(0, 4);
                userName = userName + "*******";
            }
        }
        return userName;
    }

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|6[6]|7[0135678]|8[0-9]|9[8-9])\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * @Author shenwei
     * @Date 2017/3/7 14:07
     * @Description 验证码生成
     */
    public static String getVerifyCode() {
        String str = "";
        for (Integer i = 0; i < UserContext.CHARLENGTH; i++) {
            Random random = new Random();
            Integer randomIndex = random.nextInt(UserContext.CHARSETS.length());
            str += UserContext.CHARSETS.substring(randomIndex, randomIndex + 1);
        }
        return str;
    }

    /**
     * @Author shenwei
     * @Date 2017/3/30 10:58
     * @Description 生成随机密码
     */
    public static String getRandomPass() {
        return getVerifyCode();
    }
}
