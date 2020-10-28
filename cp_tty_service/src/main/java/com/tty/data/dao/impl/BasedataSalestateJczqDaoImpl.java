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
        Query query = getQuery("FROM BasedataSalestateJczqENT WHERE issueMatchName = :issueMatchName  ");
        query.setParameter("issueMatchName", issueMatchName);
        return (BasedataSalestateJczqENT) query.uniqueResult();
    }

}
