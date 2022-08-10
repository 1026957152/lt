package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.domain.SettleAccount;
import com.lt.dom.otcenum.EnumBussinessType;

import javax.persistence.*;

@Entity
public class ApplicationFee {
    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id")
    private long id;
    private int amount;
    private int amount_refunded;

    private boolean refunded;
    private long balance;
    private long balanceTransaction;
    private long charge;
    private long refund;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount_refunded() {
        return amount_refunded;
    }

    public void setAmount_refunded(int amount_refunded) {
        this.amount_refunded = amount_refunded;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalanceTransaction(long balanceTransaction) {
        this.balanceTransaction = balanceTransaction;
    }

    public long getBalanceTransaction() {
        return balanceTransaction;
    }

    public void setCharge(long charge) {
        this.charge = charge;
    }

    public long getCharge() {
        return charge;
    }

    public void setRefund(long refund) {
        this.refund = refund;
    }

    public long getRefund() {
        return refund;
    }
}
