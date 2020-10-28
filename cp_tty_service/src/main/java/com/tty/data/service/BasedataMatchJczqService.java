package com.tty.data.service;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.model.ExtModel;
import com.tty.data.dao.entity.BasedataMatchJczqENT;

/**
 * @author zxh
 * @create 2017-02-09 16:42:58
 **/
public interface BasedataMatchJczqService {

    ExtModel listBasedataMatchJczqByHostTeamAndVisitTeam(JSONObject jsonParm, ExtModel result);

    void findBasedataMatchJczq(Integer matchId, ExtModel result);

    BasedataMatchJczqENT getCurrentJczqMatch(String traceId, String issueMatchName);


}
