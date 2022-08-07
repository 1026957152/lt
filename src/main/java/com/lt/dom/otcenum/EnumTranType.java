package com.lt.dom.otcenum;

public enum EnumTranType {//交易类型
    recharge("充值"),
    recharge_refund("充值退款"),
    recharge_refund_failed("充值退款失败"),
    withdrawal("提现申请"),
    withdrawal_failed("提现失败"),
    withdrawal_revoked("提现撤销"),
    payment("支付/收款"),
    payment_refund("退款/收到退款"),
    transfer("转账/收到转账"),
    receipts_extra("赠送"),
    credited("入账"),
    credited_refund("入账退款"),
    credited_refund_failed("入账退款失败"),




    application_fee("application_fee"),
    ;


    EnumTranType(String 入账退款) {

    }
}
