package com.tty.data.dao.impl;


import com.jdd.fm.core.db.BaseDao;
import com.tty.data.dao.BasedataSalestateJczqDao;
import com.tty.data.dao.entity.BasedataSalestateJczqENT;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * @author zhuxinhai
 */
@Repository("basedataSalestateJczqDao")
public class BasedataSalestateJczqDaoImpl extends BaseDao<BasedataSalestateJczqENT> implements BasedataSalestateJczqDao {

    @Override
    public BasedataSalestateJczqENT getBasedataSalestateJczqByIssueMatchName(String issueMatchName) {
        StringBuilder hql = new StringBuilder();
        hql.append("SELECT * from basedata_salestate_jczq WHERE s_issue_match_name = : issueMatchName");
        Query query = getQuery(hql.toString()).setString("issueMatchName", issueMatchName);
        return (BasedataSalestateJczqENT) query.uniqueResult();
    }

}
