package com.tty.common.utils;/**
 * Created by shenwei on 2017/11/7.
 */

import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.OkHttpUtil;
import com.jdd.fm.core.utils.TransferAesEncrypt;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author shenwei
 * @create 2017-11-07
 */
public class SmsUtil {
    public static String sendPost(String url, String jsonStr) {
        try {
            HashMap<String,String> map=new HashMap<>();
            map.put("body",jsonStr);
            String encryptStr = TransferAesEncrypt.aesEncrypt(GfJsonUtil.toJSONString(map), "d3YmI1BUOSE2S2YmalBVZUQ=", "UTF-8");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("request", encryptStr));
            HashMap paramsMap = new HashMap();
            paramsMap.put("request", encryptStr);
            String result = OkHttpUtil.sendKVJsonPost("", url, paramsMap, 1000L, 2000L, "utf-8", "utf-8", false);
            return result;
        } catch (Exception ex) {
            return "";
        }
    }
}
