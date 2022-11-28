package com.lt.dom.otcReq;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.ShippingCardAddress;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShippingAddressResp {

    @NotNull
    private String province;

    @NotNull
    private String prefecture;

    @NotNull
    private String county;
    @NotNull
    private String town;



    @NotNull
    private String name;

    @Length(max = 40)
    private String phone;
    @Length(max = 255)
    private String address_line1;
    @Length(max = 255)
    private String address_line2;


    @Length(max = 10)
    private String postal_code;

    @JsonProperty("default")
    private Boolean defualt_;
    private Long id;

    public static ShippingAddressResp from(ShippingCardAddress e) {
        ShippingAddressResp shippingAddressResp = new ShippingAddressResp();
        shippingAddressResp.setId(e.getId());
        shippingAddressResp.setProvince(e.getState());
        shippingAddressResp.setPrefecture(e.getCity());
        shippingAddressResp.setCounty(e.getCounty());
        shippingAddressResp.setTown(e.getTown());
        shippingAddressResp.setName(e.getFirst_name());
        shippingAddressResp.setPhone(e.getMobile());
        shippingAddressResp.setDefualt_(e.getDefault_());
        shippingAddressResp.setAddress_line1(e.getAddress_line1()==null?"": e.getAddress_line1());

        return shippingAddressResp;
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

    public void setDefualt_(Boolean defualt_) {
        this.defualt_ = defualt_;
    }

    public Boolean getDefualt_() {
        return defualt_;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
