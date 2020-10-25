package com.tty.push.outservice.impl;

import com.tty.push.dto.PushUuidUserDTO;
import com.tty.push.outservice.PushOutService;
import org.springframework.stereotype.Service;

/**
 * @Author: sunyishun
 * @Date: 2020/10/23
 * @Description:
 */
@Service("pushOutService")
public class PushOutServiceImpl implements PushOutService {
    public PushUuidUserDTO getPushUserByUuid(String uuid){
        return new PushUuidUserDTO();
    }
}
