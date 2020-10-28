package com.tty.data.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.ds.DataSource;
import com.jdd.fm.core.model.ExtModel;
import com.tty.data.dao.BasedataMatchJclqDao;
import com.tty.data.dao.entity.BasedataMatchJclqENT;
import com.tty.data.service.BasedataMatchJclqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("basedataMatchJclqService")
public class BasedataMatchJclqServiceImpl implements BasedataMatchJclqService {
    private static final Logger logger = LoggerFactory.getLogger(BasedataMatchJclqService.class);

    @Autowired
    private BasedataMatchJclqDao basedataMatchJclqDao;

    @Override
    public ExtModel listBasedataMatchJclqByHostTeamAndVisitTeam(JSONObject jsonParm, ExtModel result) {
        result.setData(basedataMatchJclqDao.listBasedataMatchJclqByHostTeamAndVisitTeam(jsonParm.getInteger("page"), jsonParm.getInteger("limit"), jsonParm.getJSONObject("data")));
        result.setTotal(basedataMatchJclqDao.listBasedataMatchJclqByHostTeamAndVisitTeamCount(jsonParm.getJSONObject("data")));
        return result;
    }

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public ExtModel listBasedataMatchJclq(JSONObject jsonParm, ExtModel result) {
        result.setData(basedataMatchJclqDao.listBasedataMatchJclq(jsonParm.getInteger("page"), jsonParm.getInteger("limit"), jsonParm.getJSONObject("data")));
        result.setTotal(basedataMatchJclqDao.listBasedataMatchJclqCount(jsonParm.getJSONObject("data")));
        return result;
    }

    @Override
    public void findBasedataMatchJclq(Integer matchId, ExtModel result) {
        BasedataMatchJclqENT ent = basedataMatchJclqDao.findBasedataMatchJclq(matchId);
        result.setData(ent);
    }

}
