package com.tty.data.dao;


import com.tty.data.dao.entity.BasedataSalestateJczqENT;

/**
 * @author zxh
 * @create 2017-02-09 16:42:58
 **/
public interface BasedataSalestateJczqDao {

    BasedataSalestateJczqENT getBasedataSalestateJczqByIssueMatchName(String issueMatchName);

}