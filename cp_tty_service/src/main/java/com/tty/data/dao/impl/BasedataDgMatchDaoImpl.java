package com.tty.data.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.utils.DateUtil;
import com.jdd.fm.core.utils.WhereUtils;
import com.tty.data.dao.BasedataDgMatchDao;
import com.tty.data.dao.entity.BasedataDgMatchENT;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author zhuxinhai
 */
@Repository("basedataDgMatchDao")
public class BasedataDgMatchDaoImpl extends BaseDao<BasedataDgMatchENT> implements BasedataDgMatchDao {

    public void saveBasedataDgMatch(BasedataDgMatchENT ent) {
        save(ent);
    }

    public void updateBasedataDgMatch(BasedataDgMatchENT ent) {
        update(ent);
    }

    public void deleteBasedataDgMatch(BasedataDgMatchENT ent) {
        delete(ent);
    }

    public void saveOrUpdateBasedataDgMatch(BasedataDgMatchENT ent) {
        saveOrUpdate(ent);
    }


    //    =========================admin==================================
    @Override
    public List<BasedataDgMatchENT> listBasedataDgMatch(Integer page, Integer limit, JSONObject data) {
        Date createEndTime = data.getDate("createTime1");
        if (createEndTime != null) {
            createEndTime = DateUtil.getEndTimeOfDay(createEndTime);
        }
        Date recommEndTime = data.getDate("recommEndTime1");
        if (recommEndTime != null) {
            recommEndTime = DateUtil.getEndTimeOfDay(recommEndTime);
        }
        WhereUtils where = WhereUtils.ins("from BasedataDgMatchENT where 1=1 ")
                .andEq("issueMatchName", data.getString("issueMatchName"))
                .andEq("playtypeId", data.getInteger("playtypeId"))
                .contains("hostTeam", data.getString("hostTeam"))
                .contains("visitTeam", data.getString("visitTeam"))
                .andGe("createTime", data.getDate("createTime"))
                .andLe("createTime", createEndTime)
                .andGe("recommEndTime", data.getDate("recommEndTime"))
                .andLe("recommEndTime", recommEndTime)
                .addOrderBy(" order by recommEndTime desc,issueMatchName desc,order ");
        return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
    }

    @Override
    public Long listBasedataDgMatchCount(JSONObject data) {
        String hql = "select count(id) from BasedataDgMatchENT where 1=1 ";
        Date createEndTime = data.getDate("createTime1");
        if (createEndTime != null) {
            createEndTime = DateUtil.getEndTimeOfDay(createEndTime);
        }
        Date recommEndTime = data.getDate("recommEndTime1");
        if (recommEndTime != null) {
            recommEndTime = DateUtil.getEndTimeOfDay(recommEndTime);
        }
        WhereUtils fullHql = WhereUtils.ins(hql)
                .andEq("issueMatchName", data.getString("issueMatchName"))
                .andEq("playtypeId", data.getInteger("playtypeId"))
                .contains("hostTeam", data.getString("hostTeam"))
                .contains("visitTeam", data.getString("visitTeam"))
                .andGe("createTime", data.getDate("createTime"))
                .andLe("createTime", createEndTime)
                .andGe("recommEndTime", data.getDate("recommEndTime"))
                .andLe("recommEndTime", recommEndTime);
        return count(fullHql.getAllSql(), fullHql.getParms()).longValue();
    }

    @Override
    @DataSource(name = "dataDataSourceRead")
    public List<BasedataDgMatchENT> listBasedataDgMatch() {
        StringBuilder hql = new StringBuilder();
        hql.append("SELECT * FROM basedata_dg_match WHERE n_status = 1 and ADDDATE(d_recomm_end_time,1) > NOW() " +
            "ORDER BY d_recomm_end_time,s_issue_match_name desc,n_order");
        return getQuery(hql.toString()).list();
    }

    @Override
    @DataSource(name = "dataDataSourceWrite")
    public void deleteBasedataDgMatchByIssueMatchName(String issueMatchName) {
        getSQLQuery("DELETE FROM basedata_dg_match WHERE s_issue_match_name=:issueMatchName")
            .setString("issueMatchName", issueMatchName).executeUpdate();
    }
}
