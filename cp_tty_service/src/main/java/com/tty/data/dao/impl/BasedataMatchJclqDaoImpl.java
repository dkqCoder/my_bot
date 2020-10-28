package com.tty.data.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.BaseDao;
import com.jdd.fm.core.utils.DateUtil;
import com.jdd.fm.core.utils.WhereUtils;
import com.tty.data.dao.BasedataMatchJclqDao;
import com.tty.data.dao.entity.BasedataMatchJclqENT;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 */
@Repository("basedataMatchJclqDao")
public class BasedataMatchJclqDaoImpl extends BaseDao<BasedataMatchJclqENT> implements BasedataMatchJclqDao {

    @Override
    public Object listBasedataMatchJclqByHostTeamAndVisitTeam(Integer page, Integer limit, JSONObject data) {
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
        WhereUtils where = WhereUtils.ins("from BasedataMatchJclqENT where 1=1 ")
            .contains("hostTeam", array[0])
            .contains("visitTeam", array[1])
            .andGt("matchStartTime", DateUtil.nowDate());
        return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
    }

    @Override
    public List<BasedataMatchJclqENT> listBasedataMatchJclq(Integer page, Integer limit, JSONObject data) {
        WhereUtils where = WhereUtils.ins("from BasedataMatchJclqENT where 1=1 ")
                .andEq("name", data.getString("name"));
        return findPageByListParam(where.getAllSql(), page, limit, where.getParms());
    }

    @Override
    public Long listBasedataMatchJclqCount(JSONObject data) {
        String hql = "select count(id) from BasedataMatchJclqENT where 1=1 ";
        WhereUtils fullHql = WhereUtils.ins(hql)
                .andEq("name", data.getString("name"));
        return count(fullHql.getAllSql(), fullHql.getParms()).longValue();
    }

    @Override
    public Long listBasedataMatchJclqByHostTeamAndVisitTeamCount(JSONObject data) {
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
        String hql = "select count(id) from BasedataMatchJclqENT where 1=1 ";
        WhereUtils fullHql = WhereUtils.ins(hql)
                .contains("hostTeam", array[0])
                .contains("visitTeam", array[1])
                .andGt("matchStartTime", DateUtil.nowDate());
        return count(fullHql.getAllSql(), fullHql.getParms()).longValue();
    }

    @Override
    public BasedataMatchJclqENT findBasedataMatchJclq(Integer matchId) {
        return get(BasedataMatchJclqENT.class, matchId);
    }


}
