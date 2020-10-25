package com.tty.user.service;

import com.alibaba.fastjson.JSONObject;
import com.jdd.fm.core.model.ExtModel;

/**
 * Created by linian on 2017/8/24.
 */
public interface UserConfigService {

    ExtModel getUserConfigAll(JSONObject data);

    ExtModel saveUserConfig(JSONObject data);
}
