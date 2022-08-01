package com.lt.dom.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class WxPayConfig {
    // 服务商 AppID
    @Value("${wxpay.app_id}")
    private String appId;
    // 商户号
    @Value("${wxpay.mch_id}")
    private String mchId;
    // 子商户号
    @Value("${wxpay.sub_mch_id}")
    private String subMchId;
    // 商户的 key(API 私钥)
    @Value("${wxpay.key}")
    private String key;
    // 证书地址
    @Value("${wxpay.cert_path}")
    private String certPath;
    // API 支付请求地址
    @Value("${wxpay.pay_url}")
    private String payUrl;
    // API 查询请求地址
    @Value("${wxpay.query_url}")
    private String queryUrl;
    // package
    @Value("${wxpay.package_value}")
    private String packageValue;
    // 回调地址
    @Value("${wxpay.tenpay_order_callback}")
    public String TENPAY_ORDER_CALLBACK;
    // 交易类型
    @Value("${wxpay.trade_type_app}")
    public String TRADE_TYPE_APP;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCertPath() {
        return certPath;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getQueryUrl() {
        return queryUrl;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getTENPAY_ORDER_CALLBACK() {
        return TENPAY_ORDER_CALLBACK;
    }

    public void setTENPAY_ORDER_CALLBACK(String TENPAY_ORDER_CALLBACK) {
        this.TENPAY_ORDER_CALLBACK = TENPAY_ORDER_CALLBACK;
    }

    public String getTRADE_TYPE_APP() {
        return TRADE_TYPE_APP;
    }

    public void setTRADE_TYPE_APP(String TRADE_TYPE_APP) {
        this.TRADE_TYPE_APP = TRADE_TYPE_APP;
    }
}