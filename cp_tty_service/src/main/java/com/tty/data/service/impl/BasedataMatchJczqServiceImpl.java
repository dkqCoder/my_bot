package com.tty.data.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.db.ds.DataSource;
import com.tty.data.dao.BasedataMatchJczqDao;
import com.tty.data.service.BasedataMatchJczqService;
import com.tty.user.common.ExtModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("basedataMatchJczqService")
public class BasedataMatchJczqServiceImpl implements BasedataMatchJczqService {

    @Autowired
    private BasedataMatchJczqDao basedataMatchJczqDao;

    @DataSource(name = DataSource.DATA_SOURCE_READ)
    @Transactional(readOnly = true)
    @Override
    public ExtModel listBasedataMatchJczqByHostTeamAndVisitTeam(JSONObject jsonParm, ExtModel result) {
        result.setData(basedataMatchJczqDao.listBasedataMatchJczqByHostTeamAndVisitTeam(
            jsonParm.getInteger("page"),
            jsonParm.getInteger("limit"),
            jsonParm.getJSONObject("data")
            )
        );
        result.setTotal(basedataMatchJczqDao.listBasedataMatchJczqByHostTeamAndVisitTeamCount(jsonParm.getJSONObject("data")));
        return result;
    }
}
