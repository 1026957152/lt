package com.lt.dom.thirdPft.domainLtToPft;

public class LtToPftRetPftCancel {

    private String orderNum;//	string	票付通订单号	是
    private String apiOrder;//		string	三方系统订单号	是
    private boolean auditRes;//		boolen	true:同意 false:拒绝	是
    private String cancelNum;//		string	本次取消数量;拒绝的情况下值为0	是
    private String canceledNum;//		string	已经取消数量(不包括本次)	是
    private String cancelMemo;//		string	备注信息	是
    private String pftSerialNum;//		string	票付通退票申请流水号	是

}
