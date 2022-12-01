package com.lt.dom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class WxConfig {

    @Value("${miniapp_release}")
    boolean miniapp_release ;

    // 服务商 AppID
    @Value("${wx.app_id}")
    private String appId;
    // 商户号
    @Value("${wx.secret}")
    private String secret;


    // 服务商 AppID
    @Value("${wxmerchant.app_id}")
    private String wxmerchant_appId;
    // 商户号
    @Value("${wxmerchant.secret}")
    private String wxmerchant_secret;

    public String getWxmerchant_appId() {
        return wxmerchant_appId;
    }

    public void setWxmerchant_appId(String wxmerchant_appId) {
        this.wxmerchant_appId = wxmerchant_appId;
    }

    public String getWxmerchant_secret() {
        return wxmerchant_secret;
    }

    public void setWxmerchant_secret(String wxmerchant_secret) {
        this.wxmerchant_secret = wxmerchant_secret;
    }

    public String getAppId() {
        if(miniapp_release){
            return appId;
        }else{
            return wxmerchant_appId;
        }

    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        if(miniapp_release){
            return secret;
        }else{
            return wxmerchant_secret;
        }

    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}