package com.lt.dom.thiirdAli;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class AliConfig {


    // 服务商 AppID
    @Value("${ali.idcard.AppKey}")
    private String idcard_appKey;
    // 商户号
    @Value("${ali.idcard.AppSecret}")
    private String idcard_appSecret;
    // 子商户号
    @Value("${ali.idcard.AppCode}")
    private String idcard_appCode;
    // 商户的 key(API 私钥)




    // 服务商 AppID
    @Value("${ali.bussiness_license.AppKey}")
    private String bussiness_license_appKey;
    // 商户号
    @Value("${ali.bussiness_license.AppSecret}")
    private String bussiness_license_appSecret;
    // 子商户号
    @Value("${ali.bussiness_license.AppCode}")
    private String bussiness_license_appCode;
    // 商户的 key(API 私钥)


    // 服务商 AppID
    @Value("${ali.idfaceIdentity.AppKey}")
    private String idfaceIdentity_appKey;
    // 商户号
    @Value("${ali.idfaceIdentity.AppSecret}")
    private String idfaceIdentity_appSecret;
    // 子商户号
    @Value("${ali.idfaceIdentity.AppCode}")
    private String idfaceIdentity_appCode;

    public String getIdcard_appKey() {
        return idcard_appKey;
    }

    public void setIdcard_appKey(String idcard_appKey) {
        this.idcard_appKey = idcard_appKey;
    }

    public String getIdcard_appSecret() {
        return idcard_appSecret;
    }

    public void setIdcard_appSecret(String idcard_appSecret) {
        this.idcard_appSecret = idcard_appSecret;
    }

    public String getIdcard_appCode() {
        return idcard_appCode;
    }

    public void setIdcard_appCode(String idcard_appCode) {
        this.idcard_appCode = idcard_appCode;
    }

    public String getBussiness_license_appKey() {
        return bussiness_license_appKey;
    }

    public void setBussiness_license_appKey(String bussiness_license_appKey) {
        this.bussiness_license_appKey = bussiness_license_appKey;
    }

    public String getBussiness_license_appSecret() {
        return bussiness_license_appSecret;
    }

    public void setBussiness_license_appSecret(String bussiness_license_appSecret) {
        this.bussiness_license_appSecret = bussiness_license_appSecret;
    }

    public String getBussiness_license_appCode() {
        return bussiness_license_appCode;
    }

    public void setBussiness_license_appCode(String bussiness_license_appCode) {
        this.bussiness_license_appCode = bussiness_license_appCode;
    }


    public String getIdfaceIdentity_appKey() {
        return idfaceIdentity_appKey;
    }

    public void setIdfaceIdentity_appKey(String idfaceIdentity_appKey) {
        this.idfaceIdentity_appKey = idfaceIdentity_appKey;
    }

    public String getIdfaceIdentity_appSecret() {
        return idfaceIdentity_appSecret;
    }

    public void setIdfaceIdentity_appSecret(String idfaceIdentity_appSecret) {
        this.idfaceIdentity_appSecret = idfaceIdentity_appSecret;
    }

    public String getIdfaceIdentity_appCode() {
        return idfaceIdentity_appCode;
    }

    public void setIdfaceIdentity_appCode(String idfaceIdentity_appCode) {
        this.idfaceIdentity_appCode = idfaceIdentity_appCode;
    }
}