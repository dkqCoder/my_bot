package com.tty.user.service;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.Result;



/**
 * Created by donne on 17/03/07.
 */
public interface UserBaseInfoService {

    /**
     * @Author shenwei
     * @Date 2017/3/13 16:55
     * @Description 102 绑定用户真实姓名和身份证
     */
    Result bindUserRealityNameAndCertCard(String userId, String realityName, String certCard, String traceId, ClientRequestHeader header);


}
