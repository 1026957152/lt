package com.lt.dom.thirdPft.domainLtToPft;

public class LtToPftRetVerify {
    private String orderNum;//	string	票付通订单号	是
    private String apiOrder;//		string	三方系统订单号	是
    private String apiRequestId;//		string	本次验证的唯一请求id	是
    private String verifyNum;//		string	本次验证数量	是
    private String verifiedNum;//		string	不包括本次已经验证数量	是
    private String verifyIDCard;//		string	本次验证的身份证号; 没有的时候传空(实名制的情况下必须传);多个逗号分隔	否
    private String verifyCode;//		string	本次验证的凭证码; 没有的时候传空（一票一码的情况下必须传;多个逗号分隔）	否

}
