package com.tty.data.service;


import com.tty.data.dao.entity.BasedataSalestateJczqENT;

/**
 * @author zxh
 * @create 2017-02-09 16:42:58
 **/
public interface BasedataSalestateJczqService {

    BasedataSalestateJczqENT getBasedataSalestateJczqByIssueMatchName(String issueMatchName, String s);

}
