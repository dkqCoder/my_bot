package com.tty.sms.outservice;

import com.tty.sms.dto.MessageDTO;

/**
 * @Author: sunyishun
 * @Date: 2020/10/23
 * @Description:
 */
public interface SmsOutService {
    boolean sendMsg(MessageDTO messageDTO);
}
