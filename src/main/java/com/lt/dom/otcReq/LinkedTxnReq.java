package com.lt.dom.otcReq;


import com.lt.dom.oct.Base;
import com.lt.dom.otcenum.EnumLinkedTxnType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;


public class LinkedTxnReq {

    private BigDecimal amount;//交易金额，单位为分。

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    private String note;//附加说明，最多 255 个 Unicode 字符。

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private Long lineId;//    关联对象的 ID，充值/充值退款为 recharge 对象，提现为 withdrawal 对象，赠送为 balance_bonus 对象，支付为 charge 对象，退款为 refund 对象，转账/分润结算为 balance_transfer 对象，入账/入账退款为 balance_settlement 对象。

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public EnumLinkedTxnType getType() {
        return type;
    }

    public void setType(EnumLinkedTxnType type) {
        this.type = type;
    }

    @Enumerated(EnumType.STRING)
    private EnumLinkedTxnType type;//    交易类型。充值：recharge，充值退款：recharge_refund，充值退款失败：recharge_refund_failed，提现申请：withdrawal，提现失败：withdrawal_failed，提现撤销：withdrawal_revoked，支付/收款：payment，退款/收到退款：payment_refund，转账/收到转账：transfer，赠送：receipts_extra，入账：credited，入账退款：credited_refund，入账退款失败：credited_refund_failed。


}
