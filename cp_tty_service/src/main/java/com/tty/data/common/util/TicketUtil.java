package com.tty.data.common.util;

import com.jdd.basedata.api.controller.vo.TicketParams;
import com.jdd.fm.core.utils.PropertiesUtil;
import com.jdd.fm.core.utils.StringUtils;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yys on 2017/7/26.
 */
public class TicketUtil {


    private static final Logger logger = LoggerFactory.getLogger(TicketUtil.class);

    public static TicketParams wrapMerchantVO(Integer action, String data, Integer merchantId) {
        String pwd = PropertiesUtil.get("ticket.password");
        TicketParams merchantVO = new TicketParams();
        merchantVO.setAction(action);
        merchantVO.setData(DESUtil.desEncrypt(data, pwd));
        merchantVO.setSign(MD5Utils.encryptT(cleanMd5Source(data), pwd).toLowerCase());
        merchantVO.setMerchantId(merchantId.toString());
        return merchantVO;
    }


    /**
     * 校验前面
     *
     * @Author wzn
     * @Date 2017/8/14
     * @Description
     */
    public static String checkSign(String data, String signCheck) {
        String pwd = PropertiesUtil.get("ticket.password");
        data = DESUtil.desDecrypt(data, pwd);

        String sign = MD5Utils.encryptT(cleanMd5Source(data), pwd).toLowerCase();
        if (sign.equals(signCheck)) {
            return data;
        }
        return "";
    }

    /**
     * 1.去空格
     * 2.去换行符
     * 3.排序
     * 4.转小写
     *
     * @param source
     * @return
     */
    private static String cleanMd5Source(String source) {
        String dest = "";
        if (StringUtils.isNotBlank(source)) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(source);
            dest = m.replaceAll("");
        }
        char[] chars = dest.toCharArray();
        Arrays.sort(chars);
        return String.valueOf(chars).toLowerCase();
    }
}
