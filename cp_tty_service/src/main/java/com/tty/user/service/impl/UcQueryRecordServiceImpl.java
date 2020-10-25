package com.tty.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.utils.HttpUtil;
import com.jdd.fm.core.utils.TransferAesEncrypt;
import com.tty.user.controller.model.params.QueryRecordParams;
import com.tty.user.dao.UserConfigDao;
import com.tty.user.dao.entity.UserConfigENT;
import com.tty.user.service.UcQueryRecordService;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jdd on 2017/11/16.
 */
@Service("ucQueryRecordService")
public class UcQueryRecordServiceImpl implements UcQueryRecordService {
    private final Logger logger = LoggerFactory.getLogger(UcQueryRecordServiceImpl.class);
    @Autowired
    private UserConfigDao userConfigDao;

    @Override
    public void saveUcQueryRecord(String operator, String menu, String adminCode, String sysCode,JSONObject param){
        QueryRecordParams ucQueryRecord = new QueryRecordParams();
        ucQueryRecord.setAdminCode(adminCode);
        ucQueryRecord.setSysCode(sysCode);
        ucQueryRecord.setMenu(menu);
        ucQueryRecord.setCreateTime(new Date());
        ucQueryRecord.setOperator(operator);
        ucQueryRecord.setCondition(JSONObject.toJSONString(param));
        String jsonStr = JSON.toJSONString(ucQueryRecord);
        logger.info("user-admin 操作日志：{}",jsonStr);
    }


}
