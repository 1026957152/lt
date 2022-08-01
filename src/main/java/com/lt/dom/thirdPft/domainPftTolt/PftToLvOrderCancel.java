package com.lt.dom.thirdPft.domainPftTolt;

public class PftToLvOrderCancel {
    private String orderNum;//	string	订单号
    private String ticketNum;//		string	门票原始数量
    private String cancelNum;//		string	请求取消数量
    private String cancelIDCard;//		string	请求取消的身份证--非实名制下不会传值(多个逗号分隔)
    private String cancelApiCode;//		string	请求取消的三方凭证码--全部取消的时候可能传空(多个逗号分隔)
    private String surplusNum;//		string	剩余数量 (退款成功后的剩余数量（如果是先验证，后修改，则是包含了验证数量的）)
    private String checkedNum;//		string	已经验证数量
    private String pftSerialNum	;//	string	票付通申请取消流水号
    private String clientId;//		string	三方系统客户标识

}
