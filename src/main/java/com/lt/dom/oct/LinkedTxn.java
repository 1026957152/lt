package com.lt.dom.oct;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.otcenum.EnumLinkedTxnType;
import com.lt.dom.otcenum.EnumTranType;

import javax.persistence.*;

@Entity
public class LinkedTxn extends Base{  //余额结算
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="invoice", nullable=false)
    private Invoice invoice;
    private boolean livemode; //是否是 live 模式。

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    private int amount;//交易金额，单位为分。

    private String description;//附加说明，最多 255 个 Unicode 字符。
    private Long txnLineId;//    关联对象的 ID，充值/充值退款为 recharge 对象，提现为 withdrawal 对象，赠送为 balance_bonus 对象，支付为 charge 对象，退款为 refund 对象，转账/分润结算为 balance_transfer 对象，入账/入账退款为 balance_settlement 对象。



    @Enumerated(EnumType.STRING)
    private EnumLinkedTxnType type;//    交易类型。充值：recharge，充值退款：recharge_refund，充值退款失败：recharge_refund_failed，提现申请：withdrawal，提现失败：withdrawal_failed，提现撤销：withdrawal_revoked，支付/收款：payment，退款/收到退款：payment_refund，转账/收到转账：transfer，赠送：receipts_extra，入账：credited，入账退款：credited_refund，入账退款失败：credited_refund_failed。


    private long user;//转出方的 user 对象的 id。



    public boolean isLivemode() {
        return livemode;
    }

    public void setLivemode(boolean livemode) {
        this.livemode = livemode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public Long getTxnLineId() {
        return txnLineId;
    }

    public void setTxnLineId(Long txnLineId) {
        this.txnLineId = txnLineId;
    }

    public EnumLinkedTxnType getType() {
        return type;
    }

    public void setType(EnumLinkedTxnType type) {
        this.type = type;
    }
}
