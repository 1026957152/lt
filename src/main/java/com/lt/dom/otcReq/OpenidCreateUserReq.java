package com.lt.dom.otcReq;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class OpenidCreateUserReq {
    @NotEmpty
    private String supplier_name;
    @NotEmpty
    private String desc;
    @NotEmpty
    private String user_name;
    @NotEmpty
    private String user_password;
    @NotEmpty
    private String user_phone;
    @NotEmpty
    private String supplier_type;
    @NotEmpty
    private String location;
    @NotEmpty
    private String bank_account_number;
    @NotEmpty
    private String account_name;
    @NotEmpty
    private String bank_name;
    @NotEmpty
    private String bussiness_license;
    @NotNull
    private Boolean term;  // 这个就是机器了啊

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getSupplier_type() {
        return supplier_type;
    }

    public void setSupplier_type(String supplier_type) {
        this.supplier_type = supplier_type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBank_account_number() {
        return bank_account_number;
    }

    public void setBank_account_number(String bank_account_number) {
        this.bank_account_number = bank_account_number;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBussiness_license() {
        return bussiness_license;
    }

    public void setBussiness_license(String bussiness_license) {
        this.bussiness_license = bussiness_license;
    }

    public Boolean getTerm() {
        return term;
    }

    public void setTerm(Boolean term) {
        this.term = term;
    }
}
