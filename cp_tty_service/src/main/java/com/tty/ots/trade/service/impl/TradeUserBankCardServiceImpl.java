package com.tty.ots.trade.service.impl;

import com.jdd.fm.core.model.ResultModel;
import com.tty.ots.trade.ent.TradeDistillValidateENT;
import com.tty.ots.trade.ent.TradeUserBankcardENT;
import com.tty.ots.trade.service.TradeUserBankCardService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: sunyishun
 * @Date: 2020/10/25
 * @Description:
 */
@Service("tradeUserBankCardService")
public class TradeUserBankCardServiceImpl implements TradeUserBankCardService {
    @Override
    public ResultModel bankEdit(String traceId, Long userId, Integer bankTypeID, Long bindId, Integer cityId, String bankName) {
        return null;
    }

    @Override
    public List<TradeUserBankcardENT> getUserBankcardsByBankcardNumber(String bankcardNumber) {
        return null;
    }

    @Override
    public void updateUserBankcardStatus(Long userId, Long bindId, int status, String msg) {

    }

    @Override
    public ResultModel bindCard(String traceId, Long userId, Integer cityId, String realName, String BankCardNumber) {
        return null;
    }

    @Override
    public ResultModel adminBindcard(String traceId, Long bindId) {
        return null;
    }

    @Override
    public ResultModel unbind(Long userId, Long bindId) {
        return null;
    }

    @Override
    public ResultModel getUserBankCardList(Long userId, String traceId) {
        return null;
    }

    @Override
    public ResultModel setDefaultbank(Long userId, Long bindId) {
        return null;
    }

    @Override
    public void oneCentDeal(String traceId, TradeDistillValidateENT validate) {

    }

    @Override
    public void oneCentSucessCall(String traceId, Long bindId) {

    }

    @Override
    public void oneCentFailureCall(Long bindId) {

    }

    @Override
    public List<TradeUserBankcardENT> listUserbankCard(Long nUserId) {
        return null;
    }

    @Override
    public ResultModel validateBankcardNumber(String traceId, String bankcardNumber) {
        return null;
    }

    @Override
    public ResultModel getUserBankCardAllList(Long userId, String traceId) {
        return null;
    }

    @Override
    public ResultModel removeBankCard(Long userId, Long bindId) {
        return null;
    }

    @Override
    public void save(TradeUserBankcardENT o) {

    }

    @Override
    public void update(TradeUserBankcardENT o) {

    }

    @Override
    public void saveOrUpdate(TradeUserBankcardENT o) {

    }

    @Override
    public void merge(TradeUserBankcardENT o) {

    }

    @Override
    public void delete(TradeUserBankcardENT o) {

    }

    @Override
    public List<TradeUserBankcardENT> find(String hql, List<Object> param) {
        return null;
    }

    @Override
    public List<TradeUserBankcardENT> find(String hql, Object... param) {
        return null;
    }

    @Override
    public List<TradeUserBankcardENT> findPageByListParam(String hql, int page, int rows, List<Object> param) {
        return null;
    }

    @Override
    public List<TradeUserBankcardENT> findPage(String hql, int page, int rows, Object... param) {
        return null;
    }

    @Override
    public TradeUserBankcardENT get(Class<TradeUserBankcardENT> c, Serializable id) {
        return null;
    }

    @Override
    public TradeUserBankcardENT get(String hql, Object... param) {
        return null;
    }

    @Override
    public TradeUserBankcardENT get(String hql, List<Object> param) {
        return null;
    }

    @Override
    public TradeUserBankcardENT load(Class<TradeUserBankcardENT> c, Serializable id) {
        return null;
    }

    @Override
    public Long count(String hql, Object... param) {
        return null;
    }

    @Override
    public Long count(String hql, List<Object> param) {
        return null;
    }

    @Override
    public Integer executeHql(String hql) {
        return null;
    }

    @Override
    public Integer executeHql(String hql, Object... param) {
        return null;
    }
}
