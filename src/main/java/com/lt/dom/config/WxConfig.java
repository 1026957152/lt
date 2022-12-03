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





    // 商户号
    @Value("${wxpay.key}")
    private String wxpaykey;

    @Value("${wxpay.mch_id}")
    private String wxpaymch_id;

    @Value("${wxpay.cert_path}")
    private String wxpaycertPath;

    public String getWxpaycertPath() {

            return wxpaycertPath;


    }

    public void setWxpaycertPath(String wxpaycertPath) {
        this.wxpaycertPath = wxpaycertPath;
    }

    public String getWxpaymch_id() {
            return wxpaymch_id;


    }

    public void setWxpaymch_id(String wxpaymch_id) {
        this.wxpaymch_id = wxpaymch_id;
    }

    public String getWxpaykey() {
            return wxpaykey;


    }

    public void setWxpaykey(String wxpaykey) {
        this.wxpaykey = wxpaykey;
    }

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