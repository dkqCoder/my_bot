package com.tty.ots.trade.outservice;

import com.tty.ots.trade.dto.UserBalanceDTO;
import com.tty.ots.trade.dto.UserBankCardDTO;

import java.util.List;

/**
 * @Author: sunyishun
 * @Date: 2020/10/24
 * @Description:
 */
public interface TradeOutService {
    List<UserBankCardDTO> getUserBankCardList(String traceId, long userID);

    UserBalanceDTO getUserBalanceDTO(String traceId, long userID);

    boolean checkPayPassword(long userID);
}
