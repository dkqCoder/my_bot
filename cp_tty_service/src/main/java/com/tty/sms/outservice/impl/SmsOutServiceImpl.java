package com.tty.sms.outservice.impl;

import com.tty.sms.dto.MessageDTO;
import com.tty.sms.outservice.SmsOutService;
import org.springframework.stereotype.Service;

/**
 * @Author: sunyishun
 * @Date: 2020/10/23
 * @Description:
 */
@Service("smsOutService")
public class SmsOutServiceImpl implements SmsOutService {
    public boolean sendMsg(MessageDTO messageDTO) {
        return true;
    }
}
