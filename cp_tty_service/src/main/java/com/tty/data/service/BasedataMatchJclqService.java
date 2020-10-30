package com.tty.data.service;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.model.ExtModel;

/**
 * @create 2017-02-09 16:42:58
 **/
public interface BasedataMatchJclqService {

    ExtModel listBasedataMatchJclqByHostTeamAndVisitTeam(JSONObject jsonParm, ExtModel result);

    ExtModel listBasedataMatchJclq(JSONObject jsonParm, ExtModel result);

    void findBasedataMatchJclq(Integer matchId, ExtModel result);


}


