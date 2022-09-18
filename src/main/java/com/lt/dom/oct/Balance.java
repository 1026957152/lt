package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumFinancialAccountStatus;
import com.lt.dom.otcenum.EnumUserType;

import javax.persistence.*;

@Entity
public class Balance {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(unique=true)
    private String code;

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

    private int available_amount;
    private int pending_amount;
    private EnumFinancialAccountStatus status;

    public int getPending_amount() {
        return pending_amount;
    }

    public void setPending_amount(int pending_amount) {
        this.pending_amount = pending_amount;
    }

    public EnumFinancialAccountStatus getStatus() {
        return status;
    }

    public void setStatus(EnumFinancialAccountStatus status) {
        this.status = status;
    }

    public int getAvailable_amount() {
        return available_amount;
    }

    public EnumUserType getType() {
        return type;
    }

    public void setType(EnumUserType type) {
        this.type = type;
    }

    public void setAvailable_amount(int balance) {
        this.available_amount = balance;
    }
}
