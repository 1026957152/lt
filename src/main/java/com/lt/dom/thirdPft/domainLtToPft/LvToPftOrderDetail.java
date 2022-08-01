package com.lt.dom.thirdPft.domainLtToPft;

public class LvToPftOrderDetail {

    private String orderNum;//	sting	票付通订单号
    private String apiOrder;//	string	三方系统订单号
    private String ticketNum;//		array	门票数量
    private String verifiedNum;//		string	已经验证数量
    private String cancelNum;//		string	已经取消数量
    private String orderStatus;//		string	订单状态 1：未使用 2：已使用 3：已取消 4：部分使用
    private String payStatus;//		string	支付状态 1：未支付 2：已支付
    private String goodsNo;//		string	三方系统产品配置码
    private String pftCode;//		string	票付通凭证码
    private String apiCode;//		string	三方系统凭证码
    private String source;//		string	订单来源：分销商名称

}
