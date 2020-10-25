package com.tty.ots.trade.outservice.impl;

import com.tty.ots.trade.dto.UserBalanceDTO;
import com.tty.ots.trade.dto.UserBankCardDTO;
import com.tty.ots.trade.outservice.TradeOutService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: sunyishun
 * @Date: 2020/10/24
 * @Description:
 */
@Service("tradeOutService")
public class TradeOutServiceImpl implements TradeOutService {
    public List<UserBankCardDTO> getUserBankCardList(String traceId, long userID) {
        return null;
    }

    public UserBalanceDTO getUserBalanceDTO(String traceId, long userID) {
        return null;
    }

    /**
     * 校验账户是否冻结
     */
    public boolean checkPayPassword(long userID) {
        return false;
    }
}
