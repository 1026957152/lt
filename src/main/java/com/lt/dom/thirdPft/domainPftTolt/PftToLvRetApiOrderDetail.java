package com.lt.dom.thirdPft.domainPftTolt;

public class PftToLvRetApiOrderDetail {
    private String orderNum;//	string	订单号	是
    private String apiOrder;//	string	三方系统订单号	是
    private String apiCode;//	string	三方系统凭证码(多个逗号分隔)	是
    private String ticketNum;//	string	门票数量	是
    private String cancelNum;//	string	已经取消数量	是
    private String verifiedNum;//	string	已经验证数量	是
    private String orderStatus;//	string	订单状态 1：未使用 2：已使用 3：已取消 4：部分使用	是
    private String payStatus;//	string	支付状态 1：未支付 2：已支付	是

}
