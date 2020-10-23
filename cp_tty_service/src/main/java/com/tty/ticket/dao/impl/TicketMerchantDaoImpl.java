package com.tty.ticket.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.utils.WhereUtils;
import com.tty.ticket.dao.TicketMerchantDao;
import com.tty.ticket.ent.TicketMerchantENT;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhuxinhai
 */
@Repository("ticketMerchantDao")
public class TicketMerchantDaoImpl extends BaseDao<TicketMerchantENT> implements TicketMerchantDao {


    @Override
    public List<TicketMerchantENT> listTicketMerchant(Integer page, Integer limit, JSONObject data) {
        WhereUtils where = WhereUtils.ins("from TicketMerchantENT where 1=1 ")
                .andEq("merchantName", data.getString("merchantName"));
        return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
    }

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Override
    public List<TicketMerchantENT> listTicketMerchant() {
        WhereUtils where = WhereUtils.ins("from TicketMerchantENT where 1=1 ");
        return find(where.getAllSql(), where.getParms());
    }

}
