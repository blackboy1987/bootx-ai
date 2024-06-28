package com.bootx.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;

/**
 * 短信发送记录
 * @author black
 */
@Entity
public class SmsLog extends OrderedEntity<Long> {

    /**
     * 手机号
     */
    @NotEmpty
    @Column(nullable = false,updatable = false)
    private String mobile;

    /**
     * 设备号
     */
    @NotEmpty
    @Column(nullable = false,updatable = false)
    private String deviceId;

    /**
     * 发送的内容
     */
    @NotEmpty
    @Column(nullable = false,updatable = false)
    private String content;

    /**
     * 发送的结果
     */
    @NotEmpty
    @Column(nullable = false,updatable = false)
    private String result;

    /**
     * 发送的ip
     */
    @NotEmpty
    @Column(nullable = false,updatable = false)
    private String ip;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
