package com.tty.user.common.utils;/**
 * Created by shenwei on 2017/3/13.
 */

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.List;


/**
 * @author shenwei
 * @create 2017-03-13
 */
public class MD5Utils {

    public static String encrypt(String input, String key) {
        input = cleanSpecial(input);
        return DigestUtils.md5Hex(input + key).toUpperCase();
    }

    public static String encrypt(String input) {
        return encrypt(input, "");
    }

    /**
     * @Author shenwei
     * @Date 2017/4/5 15:38
     * @Description 为兼容.net
     */
    public static String cleanSpecial(String word) {
        String[] special = new String[]{";--", "'", "=", " * ", "!", "<", ">", "!", "/", " and ", " union ", " select ", " from ", " order ", " by ", " update ", " insert ", " waitfor", " delay ", " exec ", "<strong>", "</strong>"};
        List<String> specialWords = Arrays.asList(special);
        for (String item : specialWords) {
            word = word.replace(item, "").replace(item.toUpperCase(), "");
        }
        return word;
    }
}
