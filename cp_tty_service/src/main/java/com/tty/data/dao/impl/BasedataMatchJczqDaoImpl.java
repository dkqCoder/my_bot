package com.tty.data.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.utils.DateUtil;
import com.tty.data.dao.BasedataMatchJczqDao;
import com.tty.data.dao.entity.BasedataMatchJczqENT;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhuxinhai
 */
@Repository("basedataMatchJczqDao")
public class BasedataMatchJczqDaoImpl extends BaseDao<BasedataMatchJczqENT> implements BasedataMatchJczqDao {

    @Override
    @DataSource(name = "dataDataSourceRead")
    @Transactional(readOnly = true)
    public List<BasedataMatchJczqENT> listBasedataMatchJczqByHostTeamAndVisitTeam(Integer page, Integer limit, JSONObject data) {
        String dataStr = data.getString("data");
        String[] array = new String[2];
        array[0] = null;
        array[1] = null;
        if (StringUtils.isNotEmpty(dataStr)) {
            String[] strs = dataStr.split(":");
            int index = 0;
            for (String a : strs) {
                array[index] = a;
                index++;
            }
        }

        StringBuilder selectSql = new StringBuilder();
        selectSql.append("select * from basedata_match_jczq where 1=1 ");
        if (array[0] != null) {
            selectSql.append(" and s_host_team like '%" + array[0] + "%'");
        }
        if (array[1] != null) {
            selectSql.append(" and s_visit_team like '%" + array[0] + "%'");
        }
        selectSql.append(" and d_match_start_time <'" + DateUtil.getNowTime() + "'");

        SQLQuery sqlQuery = getSQLQuery(selectSql.toString());
        List<BasedataMatchJczqENT> list = sqlQuery.list();

        return list;

    }

    @Override
    @DataSource(name = "dataDataSourceRead")
    @Transactional(readOnly = true)
    public Long listBasedataMatchJczqByHostTeamAndVisitTeamCount(JSONObject data) {
        String dataStr = data.getString("data");
        String[] array = new String[2];
        StringBuilder selectSql = new StringBuilder();
        array[0] = null;
        array[1] = null;
        if (StringUtils.isNotEmpty(dataStr)) {
            String[] strs = dataStr.split(":");
            int index = 0;
            for (String a : strs) {
                array[index] = a;
                index++;
            }
        }
        selectSql.append("select count(id) from basedata_match_jczq where 1=1 ");
        if (array[0] != null) {
            selectSql.append(" and s_host_team like '%" + array[0] + "%'");
        }
        if (array[1] != null) {
            selectSql.append(" and s_visit_team like '%" + array[0] + "%'");
        }
        selectSql.append(" and d_match_start_time <'" + DateUtil.getNowTime() + "'");

        SQLQuery sqlQuery = getSQLQuery(selectSql.toString());
        Number number = (Number) sqlQuery.uniqueResult();

        return number.longValue();
    }

    @Override
    @DataSource(name = "dataDataSourceRead")
    @Transactional(readOnly = true)
    public BasedataMatchJczqENT findBasedataMatchJczq(Integer matchId) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT *  FROM basedata_match_jczq  WHERE n_match_id = : matchId");
        Query query = getQuery(selectSql.toString()).setInteger("matchId", matchId);
        BasedataMatchJczqENT basedataMatchJczqENT = (BasedataMatchJczqENT) query.uniqueResult();
        return basedataMatchJczqENT;
    }

    @DataSource(name = "dataDataSourceRead")
    @Transactional(readOnly = true)
    public List<BasedataMatchJczqENT> listBasedataMatchJczqByIssueMatchName(String issueMatchName) {
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT *  FROM basedata_match_jczq  WHERE s_issue_match_name = : issueMatchName");
        Query query = getQuery(selectSql.toString()).setString("issueMatchName", issueMatchName);
        List<BasedataMatchJczqENT> list = query.list();
        return list;
    }

}
