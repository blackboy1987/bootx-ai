package com.bootx.service;

import com.bootx.entity.Member;
import com.bootx.entity.SmsLog;

/**
 * @author black
 */
public interface SmsLogService extends BaseService<SmsLog,Long>{
    void create(Member member, String deviceId, String ip, String result, String content);
}
