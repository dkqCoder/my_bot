package com.tty.data.dao;

import com.alibaba.fastjson.JSONObject;
import com.tty.data.dao.entity.BasedataDgMatchENT;
import java.util.List;

/**
 * @author zxh
 * @create 2017-02-09 16:42:58
 **/
public interface BasedataDgMatchDao {

    public void saveBasedataDgMatch(BasedataDgMatchENT ent);

    public void updateBasedataDgMatch(BasedataDgMatchENT ent);

    public void deleteBasedataDgMatch(BasedataDgMatchENT ent);

    public void saveOrUpdateBasedataDgMatch(BasedataDgMatchENT ent);

//    =========================admin==================================

    List<BasedataDgMatchENT> listBasedataDgMatch(Integer page, Integer limit, JSONObject data);

    Long listBasedataDgMatchCount(JSONObject data);

    List<BasedataDgMatchENT> listBasedataDgMatch();

    void deleteBasedataDgMatchByIssueMatchName(String issueMatchName);
}