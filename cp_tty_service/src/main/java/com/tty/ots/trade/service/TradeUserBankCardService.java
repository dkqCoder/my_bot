package com.tty.ots.trade.service;

import com.jdd.fm.core.model.ResultModel;
import com.tty.ots.trade.ent.TradeDistillValidateENT;
import com.tty.ots.trade.ent.TradeUserBankcardENT;

import java.util.List;

/**
 * @Author wenxiaoqing
 * @Date 2017/4/23
 * @Description
 */
public interface TradeUserBankCardService extends IBaseService<TradeUserBankcardENT> {

    ResultModel bankEdit(String traceId, Long userId, Integer bankTypeID, Long bindId, Integer cityId, String bankName);

    List<TradeUserBankcardENT> getUserBankcardsByBankcardNumber(String bankcardNumber);

    void updateUserBankcardStatus(Long userId, Long bindId, int status, String msg);

    /**
     * 绑定银行卡
     * @param userId
     * @param cityId
     * @param realName
     * @param BankCardNumber
     * @return
     */
    ResultModel bindCard(String traceId, Long userId, Integer cityId, String realName, String BankCardNumber);

    ResultModel adminBindcard(String traceId, Long bindId);
    /**
     * 解绑银行卡
     * @param userId   用户id
     * @param bindId   主键
     */
    ResultModel unbind(Long userId, Long bindId);

    /**
     * 获取用户绑定银行卡
     * @param traceId
     * @param userId
     */
    ResultModel getUserBankCardList(Long userId, String traceId);

    /**
     * 设置 默认银行
     * @param userId
     * @param bindId
     */
    ResultModel setDefaultbank(Long userId, Long bindId);

    /**
     * 一分钱打款
     * @return
     */
    void oneCentDeal(String traceId, TradeDistillValidateENT validate);

    /**
     * 1分钱打款成功 回调
     * @param bindId   pk
     */
    void oneCentSucessCall(String traceId, Long bindId);

    /**
     * 1分钱打款失败 回调
     * @param bindId
     */
    void oneCentFailureCall(Long bindId);

    /**
     * 获取 用户 银行卡列表
     * @param nUserId
     * @return
     */
    List<TradeUserBankcardENT> listUserbankCard(Long nUserId);

    /**
     * 验证银行卡号
     * @param bankcardNumber
     * @return
     */
    ResultModel validateBankcardNumber(String traceId, String bankcardNumber);

    /**
     * 获取用户绑定银行卡,包括审核未通过的
     * @param traceId
     * @param userId
     */
    ResultModel getUserBankCardAllList(Long userId, String traceId);

    /**
     * 删除审核未通过的银行卡
     * @param userId
     * @param bindId
     * @return
     */
    ResultModel removeBankCard(Long userId, Long bindId);


}
