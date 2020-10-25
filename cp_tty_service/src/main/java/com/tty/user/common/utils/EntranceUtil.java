package com.tty.user.common.utils;

import com.jdd.fm.core.utils.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author shenwei
 * @create 2017-07-19
 */
public class EntranceUtil {

    private static final Logger logger = LoggerFactory.getLogger(EntranceUtil.class);

    public static Boolean forbiddenEntrance(String cmdName, String traceId) {
        try {
            String forbiddenEntrance = PropertiesUtil.get("user.forbidden.entrance");
            if (StringUtils.isNotEmpty(forbiddenEntrance) && StringUtils.isNotEmpty(cmdName)) {
                if (Arrays.stream(forbiddenEntrance.toLowerCase().split(",")).filter(x -> x.equals(cmdName.toLowerCase())).findAny().isPresent()) {
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error("[注册渠道过滤异常] traceId:{}", traceId);
        }
        return false;
    }

    public static Boolean forbiddenUUID(String platform, String uuid, String traceId) {
        try {
            if (StringUtils.isNotEmpty(platform) && StringUtils.isNotEmpty(uuid)) {
                if (uuid.equals("3C075461B9B0")) {
                    return false;
                }
                if (platform.toLowerCase().equals("android")) {
                    String flag = uuid.split("-")[0];
                    if (flag.equals("ffffffff") || flag.equals("00000000")) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("[注册设备过滤异常] traceId:{}", traceId);
        }
        return false;
    }
}
