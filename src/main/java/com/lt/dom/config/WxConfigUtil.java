package com.lt.dom.config;

import com.github.wxpay.sdk.WXPayConfig;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class WxConfigUtil implements WXPayConfig {
    private byte[] certData;

    public static String APP_ID; // 服务商的 APPID(公众平台服务号)
    public static String MCH_ID; // 商户号
    public static String KEY; // 服务商密钥

    public WxConfigUtil(String path,String appId,String mchId,String key) throws Exception {
        APP_ID = appId;
        MCH_ID = mchId;
        KEY = key;
        String certPath = path;
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    @Override
    public String getAppID() { return APP_ID; }

    @Override
    public String getMchID() { return MCH_ID; }

    @Override
    public String getKey() { return KEY; }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}