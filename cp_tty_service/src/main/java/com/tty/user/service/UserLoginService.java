package com.tty.user.service;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.Result;

/**
 * Created by shenwei on 2017/3/29.
 */
public interface UserLoginService {
    /**
     * @Author shenwei
     * @Date 2017/3/13 11:12
     * @Description 1011用户登录
     */
    Result login(String token, String userName, String password, String userId, String uuid, Integer userType, String traceId, ClientRequestHeader header);

}
