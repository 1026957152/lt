package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumTranType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class TransactionEntry {  //余额结算
    @Version
    private Integer version;
    @Id
    private long id; // 用户余额转账对象 ID，由Ping++ 生成。

    private boolean livemode; //是否是 live 模式。


    private int amount;//交易金额，单位为分。
    private int available_balance;//该笔交易发生后，用户的可用余额。
    private String description;//附加说明，最多 255 个 Unicode 字符。
    private long transactionId;//    关联对象的 ID，充值/充值退款为 recharge 对象，提现为 withdrawal 对象，赠送为 balance_bonus 对象，支付为 charge 对象，退款为 refund 对象，转账/分润结算为 balance_transfer 对象，入账/入账退款为 balance_settlement 对象。

    private EnumTranType type;//    交易类型。充值：recharge，充值退款：recharge_refund，充值退款失败：recharge_refund_failed，提现申请：withdrawal，提现失败：withdrawal_failed，提现撤销：withdrawal_revoked，支付/收款：payment，退款/收到退款：payment_refund，转账/收到转账：transfer，赠送：receipts_extra，入账：credited，入账退款：credited_refund，入账退款失败：credited_refund_failed。

    private long user;//转出方的 user 对象的 id。
    private long balance;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public int getAvailable_balance() {
        return available_balance;
    }

    public void setAvailable_balance(int available_balance) {
        this.available_balance = available_balance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long source) {
        this.transactionId = source;
    }

    public EnumTranType getType() {
        return type;
    }

    public void setType(EnumTranType type) {
        this.type = type;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getBalance() {
        return balance;
    }

    private int inbound_pending;//交易金额，单位为分。
    private int outbound_pending;//交易金额，单位为分。

           // "inbound_pending": 0,
           //         "outbound_pending": 100
}
