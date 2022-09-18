package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumSplitBearerType;
import com.lt.dom.otcenum.EnumSpliteType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Splits {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    private long id;
    private long payment;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    private EnumSpliteType type;
    @NotNull
    private Integer shares;
    private long balance;
    private long subaccount;

    @NotNull
    private EnumSplitBearerType bearer_type;


    private long bearer_subaccount;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public EnumSpliteType getType() {
        return type;
    }

    public void setType(EnumSpliteType type) {
        this.type = type;
    }

    public Integer getShares() {
        return shares;
    }

    public void setShares(Integer shares) {
        this.shares = shares;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getSubaccount() {
        return subaccount;
    }

    public void setSubaccount(long subaccount) {
        this.subaccount = subaccount;
    }

    public EnumSplitBearerType getBearer_type() {
        return bearer_type;
    }

    public void setBearer_type(EnumSplitBearerType bearer_type) {
        this.bearer_type = bearer_type;
    }

    public long getBearer_subaccount() {
        return bearer_subaccount;
    }

    public void setBearer_subaccount(long bearer_subaccount) {
        this.bearer_subaccount = bearer_subaccount;
    }

    public void setPayment(long payment) {
        this.payment = payment;
    }

    public long getPayment() {
        return payment;
    }
}
