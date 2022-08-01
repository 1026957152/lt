package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumUserType;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Balance {
    @Id
    private long id;

    private EnumUserType type; //用户分为 customer 类型和 business 类型，customer
    private long user;

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    private int balance;

    public int getBalance() {
        return balance;
    }

    public EnumUserType getType() {
        return type;
    }

    public void setType(EnumUserType type) {
        this.type = type;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
