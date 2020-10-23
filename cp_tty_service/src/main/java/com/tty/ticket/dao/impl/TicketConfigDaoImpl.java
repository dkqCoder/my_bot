package com.tty.ticket.dao.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.utils.WhereUtils;
import com.tty.ticket.dao.TicketConfigDao;
import com.tty.ticket.dao.entity.TicketConfigENT;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author zhuxinhai
 *
 */
@Repository("ticketConfigDao")
public class TicketConfigDaoImpl extends BaseDao<TicketConfigENT> implements TicketConfigDao {

    public void saveTicketConfig(TicketConfigENT ent) {
        save(ent);
    }

    public void updateTicketConfig(TicketConfigENT ent) {
        update(ent);
    }

    public void saveOrUpdateTicketConfig(TicketConfigENT ent) {
        saveOrUpdate(ent);
    }

    @Override
    public void deleteTicketConfig(String code) {
        getSQLQuery("delete from ticket_config where s_code=:code").setString("code", code).executeUpdate();
    }

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    public TicketConfigENT getOrderConfigENTByCode(String code) {
        List<TicketConfigENT> ticketConfigENTS = find("from TicketConfigENT where code=? ", code);
        if (CollectionUtils.isEmpty(ticketConfigENTS)) {
            return null;
        }
        return ticketConfigENTS.get(0);
    }

    @Override
    public List<TicketConfigENT> listTicketConfig(Integer page, Integer limit, JSONObject data) {
        WhereUtils where = WhereUtils.ins("from TicketConfigENT where 1=1 ")
                .andEq("code", data.getString("code"));
        return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
    }

     @Override
     public Long listTicketConfigCount(JSONObject data) {
        String hql = "select count(id) from TicketConfigENT where 1=1 ";
        WhereUtils fullHql = WhereUtils.ins(hql)
         .andEq("code",data.getString("code"));
        return count(fullHql.getAllSql(), fullHql.getParms()).longValue();
     }

    @Override
    public List<TicketConfigENT> listAllTicketConfig() {
        return getQuery("from TicketConfigENT").list();
    }

}
