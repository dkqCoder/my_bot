package com.tty.push.outservice;

import com.tty.push.dto.PushUuidUserDTO;

/**
 * @Author: sunyishun
 * @Date: 2020/10/23
 * @Description:
 */
public interface PushOutService {
    PushUuidUserDTO getPushUserByUuid(String uuid);
}
