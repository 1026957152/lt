package com.lt.dom.otcenum;

public enum EnumFlowType {
    inbound_transfer("The Transaction is associated with an InboundTransfer"),
    issuing_authorization("he Transaction is associated with an Issuing authorization."),
    outbound_payment("The Transaction is associated with an OutboundPayment"),
    outbound_transfer("The Transaction is associated with an OutboundTransfer"),
    received_credit("The Transaction is associated with a ReceivedCredit"),

    credit_reversal("The Transaction is associated with a CreditReversal"),
    debit_reversal("The Transaction is associated with a DebitReversal"),
    other("The Transaction is associated with some other money movement not listed above"),




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

            ;



    EnumFlowType(String barcode) {

    }
}
