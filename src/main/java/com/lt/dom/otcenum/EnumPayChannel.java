package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public enum EnumPayChannel {
    alipay("支付宝 App 支付"),
    alipay_wap("支付宝手机网站支付"),
    alipay_qr("支付宝扫码支付"),
    alipay_scan("支付宝条码支付"),
    alipay_lite("支付宝小程序支付"),
    alipay_pc_direct("支付宝电脑网站支付"),
    wx("微信 App 支付"),
    wx_pub("微信 JSAPI 支付"),
    wx_pub_qr("微信 Native 支付"),
    wx_pub_scan("微信付款码支付"),
    wx_wap("微信 H5 支付"),
    wx_lite("微信小程序支付"),
    qpay("QQ 钱包 App 支付"),
    qpay_pub("QQ 钱包公众号支付"),
    upacp("银联手机控件支付（银联 App 支付）"),
    upacp_pc("银联网关支付（银联 PC 网页支付）"),
    upacp_qr("银联二维码（主扫）"),
    upacp_scan("银联二维码（被扫）"),
    upacp_wap("银联手机网站支付"),
    upacp_b2b("银联企业网银支付（银联 B2B PC 网页支付）"),
    cp_b2b("银联电子企业网银支付（银联电子 B2B PC 网页支付）"),
    applepay_upacp("Apple Pay"),
    cmb_wallet("招行一网通"),
    cmb_pc_qr("招行 PC 扫码支付"),
    bfb_wap("百度钱包"),
    jdpay_wap("京东支付"),
    yeepay_wap("易宝支付"),
    isv_qr("线下扫码（主扫）"),
    isv_scan("线下扫码（被扫）"),
    isv_wap("线下扫码（固定码）"),
    isv_lite("线下小程序支付"),
    ccb_pay("建行 App 支付"),
    ccb_qr("建行二维码支付"),
    cmpay("移动和包支付"),
    coolcredit("库分期"),
    cb_alipay("跨境支付宝 App 支付"),
    cb_alipay_wap("跨境支付宝手机网站支付"),
    cb_alipay_qr("跨境支付宝扫码支付"),
    cb_alipay_scan("跨境支付宝条码支付"),
    cb_alipay_pc_direct("跨境支付宝电脑网站支付"),
    cb_wx("跨境微信 App 支付"),
    cb_wx_pub("跨境微信 JSAPI 支付"),
    cb_wx_pub_qr("跨境微信 Native 支付"),
    cb_wx_pub_scan("跨境微信付款码支付"),
    paypal("PayPal"),
    balance("余额"),
    free("免费"),
    hero_card("hero_card"),
    cash("cash"),
    check("cash"),
    bank_transfer("bank transfer"),

    ;


    public static List<EnumResp> from() {
        return Arrays.asList(EnumPayChannel.values()).stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }

    public static List<EnumResp> from(List<EnumPayChannel> enumPayChannels) {
        return enumPayChannels.stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumPayChannel(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.payment.channel."
                + this.name());
        return displayStatusString;
    }


}
