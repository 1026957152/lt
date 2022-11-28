package com.lt.dom.controllerOct;

import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class MyConfig extends WXPayConfig {

    // 商户号
    @Value("${wxpay.cert_path}")
    private String certPath;

    private byte[] certData;

    public MyConfig() throws Exception {
   String certPath ="C:\\Users\\1\\IdeaProjects\\lt\\src\\main\\resources\\apiclient_cert.p12";// "E:\\work\\dom\\src\\main\\resources\\apiclient_cert.p12";
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public String getAppID() {
        return "wx626ed6460e6dd971";
    }

    public String getMchID() {
        return "1630279599";
    }

    public String getKey() {
        return "bitqqf8cie5mia20fb0yugc3m715v2eo";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    @Override
    protected IWXPayDomain getWXPayDomain() {
        // 这个方法需要这样实现, 否则无法正常初始化WXPay
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {

            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }

            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
/*————————————————
        版权声明：本文为CSDN博主「大山是只猫」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
        原文链接：https://blog.csdn.net/weixin_42161659/article/details/89097665*/
    }

    @Override
    public WXPayConstants.SignType getSignType() {
        return WXPayConstants.SignType.MD5;
    }
}