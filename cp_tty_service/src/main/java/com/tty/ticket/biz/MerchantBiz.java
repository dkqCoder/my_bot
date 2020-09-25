package com.tty.ticket.biz;

import com.jdd.fm.core.log.LogExceptionStackTrace;
import com.jdd.fm.core.utils.GfJsonUtil;
import com.jdd.fm.core.utils.StringUtils;
import com.tty.common.utils.DESUtil;
import com.tty.common.utils.Md5Util;
import com.tty.ticket.api.vo.MerchantResultVO;
import com.tty.ticket.api.vo.MerchantVO;
import com.tty.ticket.ent.TicketMerchantENT;
import com.tty.ticket.service.TicketMerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yys on 2017/7/26.
 */
@Component("merchantBiz")
public class MerchantBiz {

    @Autowired
    private TicketMerchantService ticketMerchantService;

    private static final Logger logger = LoggerFactory.getLogger(MerchantBiz.class);

    public MerchantVO wrapMerchantVO(Integer action, String data, Integer merchantId, String pwd) {
        MerchantVO merchantVO = new MerchantVO();
        merchantVO.setAction(action);
        merchantVO.setData(DESUtil.desEncrypt(data, pwd));
        merchantVO.setSign(Md5Util.encrypt(cleanMd5Source(data), pwd).toLowerCase());
        merchantVO.setMerchantId(merchantId.toString());
        return merchantVO;
    }

    public MerchantResultVO checkSign(MerchantVO merchantVO) {
        MerchantResultVO merchantResultVO = new MerchantResultVO();
        try {
            TicketMerchantENT currentMerchantENT = ticketMerchantService.getCurrentMerchantENT(Integer.valueOf(merchantVO.getMerchantId()));
            String password = currentMerchantENT.getPassword();
            String data = DESUtil.desDecrypt(merchantVO.getData(), password);
            String sign = Md5Util.encrypt(cleanMd5Source(data), password).toLowerCase();
            if (sign.equals(merchantVO.getSign())) {
                merchantResultVO.setData(data);
                merchantResultVO.setSuccess(true);
            } else {
                merchantResultVO.setSuccess(false);
                merchantResultVO.setMsg("签名验证失败");
            }
        } catch (Exception e) {
            logger.error("[出票系统][签名验证]处理异常json={},stackTrace={}", GfJsonUtil.toJSONString(merchantVO), LogExceptionStackTrace.erroStackTrace(e));
            merchantResultVO.setSuccess(false);
            merchantResultVO.setMsg("签名验证异常");
        }
        return merchantResultVO;
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
    private String cleanMd5Source(String source) {
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
