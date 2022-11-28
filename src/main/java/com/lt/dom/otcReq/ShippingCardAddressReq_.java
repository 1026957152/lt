package com.lt.dom.otcReq;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class ShippingCardAddressReq_ {

    @NotNull
    private String province;

    @NotNull
    private String prefecture;

    @NotNull
    private String county;

    private String town;



    @NotNull
    private String name;
    @NotNull
    @Length(max = 40)
    private String phone;
    @NotNull
    @Length(max = 255)
    private String address_line1;
    @Length(max = 255)
    private String address_line2;


    @Length(max = 10)
    private String postal_code;


    @JsonProperty("default")
    private Boolean default_;

    public Boolean getDefault_() {
        return default_;
    }

    public void setDefault_(Boolean default_) {
        this.default_ = default_;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }
}
