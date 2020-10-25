package com.tty.ots.trade.service.impl;

import com.tty.ots.trade.ent.TradeFundENT;
import com.tty.ots.trade.service.TradeInnerFundService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: sunyishun
 * @Date: 2020/10/25
 * @Description:
 */
@Service("tradeInnerFundService")
public class TradeInnerFundServiceImpl implements TradeInnerFundService {
    @Override
    public TradeFundENT getTradeFundENT(Long userId) {
        return null;
    }

    @Override
    public BigDecimal getUserBalance(String traceId, Long userId) throws Exception {
        return null;
    }

    @Override
    public Boolean winAward(String traceId, Long userId, Long sourceId, BigDecimal winMoney) throws Exception {
        return null;
    }

    @Override
    public boolean payScheme(String traceId, BigDecimal schemeMoney, String cmdName, Long userId, String userName, Long schemeId, Long hongbaoId, Long lotteryId, int schemeType, int recommnedType) throws Exception {
        return false;
    }

    @Override
    public void save(TradeFundENT o) {

    }

    @Override
    public void update(TradeFundENT o) {

    }

    @Override
    public void saveOrUpdate(TradeFundENT o) {

    }

    @Override
    public void merge(TradeFundENT o) {

    }

    @Override
    public void delete(TradeFundENT o) {

    }

    @Override
    public List<TradeFundENT> find(String hql, List<Object> param) {
        return null;
    }

    @Override
    public List<TradeFundENT> find(String hql, Object... param) {
        return null;
    }

    @Override
    public List<TradeFundENT> findPageByListParam(String hql, int page, int rows, List<Object> param) {
        return null;
    }

    @Override
    public List<TradeFundENT> findPage(String hql, int page, int rows, Object... param) {
        return null;
    }

    @Override
    public TradeFundENT get(Class<TradeFundENT> c, Serializable id) {
        return null;
    }

    @Override
    public TradeFundENT get(String hql, Object... param) {
        return null;
    }

    @Override
    public TradeFundENT get(String hql, List<Object> param) {
        return null;
    }

    @Override
    public TradeFundENT load(Class<TradeFundENT> c, Serializable id) {
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
