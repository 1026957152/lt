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


    @Value("${wxmerchant_wxpay.key}")
    private String wxmerchant_wxpaykey;

    @Value("${wxmerchant_wxpay.mch_id}")
    private String wxmerchant_wxpaymch_id;

    @Value("${wxmerchant_wxpay.cert_path}")
    private String wxmerchant_wxpaycertPath;




    // 商户号
    @Value("${wxpay.key}")
    private String wxpaykey;

    @Value("${wxpay.mch_id}")
    private String wxpaymch_id;

    @Value("${wxpay.cert_path}")
    private String wxpaycertPath;

    public String getWxpaycertPath() {
        if(miniapp_release){
            return wxpaycertPath;
        }else{
            return wxmerchant_wxpaycertPath;
        }

    }

    public void setWxpaycertPath(String wxpaycertPath) {
        this.wxpaycertPath = wxpaycertPath;
    }

    public String getWxpaymch_id() {
        if(miniapp_release){
            return wxpaymch_id;
        }else{
            return wxmerchant_wxpaymch_id;
        }

    }

    public void setWxpaymch_id(String wxpaymch_id) {
        this.wxpaymch_id = wxpaymch_id;
    }

    public String getWxpaykey() {
        if(miniapp_release){
            return wxpaykey;
        }else{
            return wxmerchant_wxpaykey;
        }

    }

    public void setWxpaykey(String wxpaykey) {
        this.wxpaykey = wxpaykey;
    }

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