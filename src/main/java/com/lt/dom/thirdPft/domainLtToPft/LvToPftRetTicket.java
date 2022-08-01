package com.lt.dom.thirdPft.domainLtToPft;

public class LvToPftRetTicket {

    private String orderNum;//	string	票付通订单号	是
    private String apiOrder;//	s	string	三方系统订单号	是
    private String apiCode;//	s	string	三方系统凭证码(多个逗号分隔)	是
    private String apiQrcode;//	s	string	三方系统二维码图片链接(多个逗号分隔)--图片需要可下载	否
    private String touristIDCard;//	s	string	一一对应凭证码顺序身份证(多个逗号分隔) --实名制的情况下	否

}
