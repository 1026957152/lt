package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumFinancialAccountStatus;
import com.lt.dom.otcenum.EnumUserType;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Balance {
    @Id
    private long id;
    @Version
    private Integer version;

    private boolean available;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private EnumUserType type; //用户分为 customer 类型和 business 类型，customer
    private long user;

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    private int amount;

    private EnumFinancialAccountStatus status;



    public int getAmount() {
        return amount;
    }

    public EnumUserType getType() {
        return type;
    }

    public void setType(EnumUserType type) {
        this.type = type;
    }

    public void setAmount(int balance) {
        this.amount = balance;
    }
}
