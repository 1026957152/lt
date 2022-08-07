package com.lt.dom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class WxConfig {
    // 服务商 AppID
    @Value("${wx.app_id}")
    private String appId;
    // 商户号
    @Value("${wx.secret}")
    private String secret;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}