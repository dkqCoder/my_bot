package com.tty.data.service.impl;

import com.jdd.fm.core.db.ds.DataSource;
import com.tty.data.dao.BasedataSalestateJczqDao;
import com.tty.data.dao.entity.BasedataSalestateJczqENT;
import com.tty.data.service.BasedataSalestateJczqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("basedataSalestateJczqService")
public class BasedataSalestateJczqServiceImpl implements BasedataSalestateJczqService {

    private static final Logger logger = LoggerFactory.getLogger(BasedataSalestateJczqService.class);

    @Autowired
    private BasedataSalestateJczqDao basedataSalestateJczqDao;

    @Transactional
    @DataSource(name = DataSource.DATA_SOURCE_WRITE)
    @Override
    public BasedataSalestateJczqENT getBasedataSalestateJczqByIssueMatchName(String issueMatchName, String s) {
        return basedataSalestateJczqDao.getBasedataSalestateJczqByIssueMatchName(issueMatchName);
    }

}
