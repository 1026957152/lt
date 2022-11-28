package com.lt.dom.oct;


import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class ShippingCardAddress extends Base{


    private Long user;
    @NotNull
    private String first_name;

    @Length(max = 40)
    private String mobile;
    @Length(max = 255)
    private String address_line1;
    @Length(max = 255)
    private String address_line2;
    @Length(max = 40)
    private String city;
    @Length(max = 32)
    private String state;
    @Length(max = 10)
    private String postal_code;
    private long supplier;
    private String code;
    private String county;
    private String town;

    private Boolean default_;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getAddress_line2() {
        return address_line2;
    }

    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public void setSupplier(long supplier) {

        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setCode(String code) {

        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getUser() {
        return user;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCounty() {
        return county;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getTown() {
        return town;
    }

    public void setDefault_(Boolean default_) {
        this.default_ = default_;
    }

    public Boolean getDefault_() {
        return default_;
    }
}
