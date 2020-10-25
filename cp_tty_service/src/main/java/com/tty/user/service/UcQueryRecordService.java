package com.tty.user.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by jdd on 2017/11/16.
 */
public interface UcQueryRecordService {
    void saveUcQueryRecord(String operator, String menu, String adminCode, String sysCode,JSONObject param);
}
