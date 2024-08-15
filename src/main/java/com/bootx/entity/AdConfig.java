package com.bootx.entity;

import jakarta.persistence.Entity;

/**
 * @author black
 */
@Entity
public class AdConfig extends BaseEntity<Long> {

    /**
     *  cpId
     */
    private String adId;

    /**
     *  mediaId
     */
    private String mediaId;

    /**
     *开屏广告
     */
    private String splashAdId;

    /**
     *动态信息流
     */
    private String dynamicExpressAdId;

    /**
     * 激励视频
     */
    private String rewardVideoAdId;

    /**
     * Banner广告
     */
    private String bannerId;

    /**
     * 插屏广告
     */
    private String interactionAdId;

    /**
     * 全屏视频
     */
    private String fullScreenVideoAdId;

    /**
     * 是否开启广告
     */
    private Boolean isOpen;

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getSplashAdId() {
        return splashAdId;
    }

    public void setSplashAdId(String splashAdId) {
        this.splashAdId = splashAdId;
    }

    public String getDynamicExpressAdId() {
        return dynamicExpressAdId;
    }

    public void setDynamicExpressAdId(String dynamicExpressAdId) {
        this.dynamicExpressAdId = dynamicExpressAdId;
    }

    public String getRewardVideoAdId() {
        return rewardVideoAdId;
    }

    public void setRewardVideoAdId(String rewardVideoAdId) {
        this.rewardVideoAdId = rewardVideoAdId;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getInteractionAdId() {
        return interactionAdId;
    }

    public void setInteractionAdId(String interactionAdId) {
        this.interactionAdId = interactionAdId;
    }

    public String getFullScreenVideoAdId() {
        return fullScreenVideoAdId;
    }

    public void setFullScreenVideoAdId(String fullScreenVideoAdId) {
        this.fullScreenVideoAdId = fullScreenVideoAdId;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }
}
