package com.tty.user.service.handler;

import com.jdd.fm.core.model.ClientRequestHeader;
import com.tty.common.utils.Result;

/**
 * Created by shenwei on 2017/3/16.
 */
public interface RegisterHandler {
    
    Result register(String traceId, ClientRequestHeader header, String params);
}
