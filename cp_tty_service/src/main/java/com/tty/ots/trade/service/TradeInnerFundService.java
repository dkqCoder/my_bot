package com.tty.ots.trade.service;


import com.tty.ots.trade.ent.TradeFundENT;

import java.math.BigDecimal;

public interface TradeInnerFundService extends IBaseService<TradeFundENT> {

    TradeFundENT getTradeFundENT(Long userId);

    /**
     * 获取用户余额
     *
     * @param traceId
     * @param userId
     * @return
     */
    BigDecimal getUserBalance(String traceId, Long userId) throws Exception;

    /**
     * 派奖增加余额
     *
     * @param traceId
     * @param userId
     * @param sourceId
     * @param winMoney
     * @return
     */
    Boolean winAward(String traceId, Long userId, Long sourceId, BigDecimal winMoney) throws Exception;

    /**
     * 方案购买付款
     *
     * @param traceId
     * @param schemeMoney
     * @param userId
     * @param userName
     * @param schemeId
     * @param hongbaoId
     * @param lotteryId
     * @param schemeType  投注类型
     * @return
     */
    boolean payScheme(String traceId, BigDecimal schemeMoney, String cmdName, Long userId, String userName,
                      Long schemeId, Long hongbaoId, Long lotteryId, int schemeType, int recommnedType) throws Exception;
}
