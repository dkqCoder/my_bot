package com.tty.data.dao;

import com.alibaba.fastjson.JSONObject;
import com.tty.data.dao.entity.BasedataMatchJczqENT;
import java.util.List;


/**
 * @author zxh
 * @create 2017-02-09 16:42:58
 **/
public interface BasedataMatchJczqDao {

    List<BasedataMatchJczqENT> listBasedataMatchJczqByHostTeamAndVisitTeam(Integer page, Integer limit, JSONObject data);

    Long listBasedataMatchJczqByHostTeamAndVisitTeamCount(JSONObject data);
}