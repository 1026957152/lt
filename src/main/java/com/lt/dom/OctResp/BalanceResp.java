package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Balance;
import com.lt.dom.otcenum.EnumFinancialAccountStatus;
import com.lt.dom.otcenum.EnumUserType;

import javax.persistence.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class BalanceResp {



    private boolean available;
    private String status_text;

    public static BalanceResp from(Balance balance) {

        BalanceResp balanceResp = new BalanceResp();
        balanceResp.setType(balance.getType());
        balanceResp.setAvailable_amount(balance.getAvailable_amount());
        balanceResp.setPending_amount(balance.getPending_amount());
        balanceResp.setStatus(balance.getStatus());
        balanceResp.setStatus_text(balance.getStatus().toString());
        return balanceResp;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }
}
