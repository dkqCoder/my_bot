package com.tty.ticket.common.util;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yys on 2017/7/5.
 */
public class TicketUtil {

    public static String getShardByOrderNo(Long schemeId) {
        try {
            return String.valueOf(schemeId / 100L % 10L);
        } catch (Exception var4) {
            return "";
        }
    }


}
