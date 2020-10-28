package com.tty.data.dao;

import com.alibaba.fastjson.JSONObject;
import com.tty.data.dao.entity.BasedataMatchJclqENT;
import java.util.List;

/**
 * @author zxh
 * @create 2017-02-09 16:42:58
 **/
public interface BasedataMatchJclqDao {

    List<BasedataMatchJclqENT> listBasedataMatchJclq(Integer page, Integer limit, JSONObject data);

    Long listBasedataMatchJclqCount(JSONObject data);

    Long listBasedataMatchJclqByHostTeamAndVisitTeamCount(JSONObject data);

    Object listBasedataMatchJclqByHostTeamAndVisitTeam(Integer page, Integer limit, JSONObject data);

    BasedataMatchJclqENT findBasedataMatchJclq(Integer matchId);


}