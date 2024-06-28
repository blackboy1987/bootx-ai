package com.bootx.service.impl;

import com.bootx.entity.Member;
import com.bootx.entity.SmsLog;
import com.bootx.service.SmsLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author black
 */
@Service
public class SmsLogServiceImpl extends BaseServiceImpl<SmsLog,Long> implements SmsLogService {
    @Override
    @Async
    public void create(Member member, String deviceId, String ip, String result, String content) {
        SmsLog smsLog = new SmsLog();
        smsLog.setContent(content);
        smsLog.setIp(ip);
        smsLog.setDeviceId(deviceId);
        smsLog.setMobile(member.getMobile());
        smsLog.setResult(result);
        super.save(smsLog);
    }
}
