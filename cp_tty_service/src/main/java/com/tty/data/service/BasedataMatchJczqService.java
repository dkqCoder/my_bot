package com.tty.data.service;

import com.alibaba.fastjson.JSONObject;
import com.tty.user.common.ExtModel;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zxh
 * @create 2017-02-09 16:42:58
 **/
public interface BasedataMatchJczqService {

    ExtModel listBasedataMatchJczqByHostTeamAndVisitTeam(JSONObject jsonParm, ExtModel result);


}
