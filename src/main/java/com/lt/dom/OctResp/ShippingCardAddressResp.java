package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.ShippingCardAddress;
import com.lt.dom.otcReq.ShippingCardAddressReq;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShippingCardAddressResp extends Base {



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

    public static ShippingCardAddressResp from(ShippingCardAddress return_address) {
        ShippingCardAddressResp shippingCardAddressResp = new ShippingCardAddressResp();
        shippingCardAddressResp.setAddress_line1(return_address.getAddress_line1()==null?"": return_address.getAddress_line1());
        shippingCardAddressResp.setCity(return_address.getCity());
        shippingCardAddressResp.setState(return_address.getState());
        shippingCardAddressResp.setMobile(return_address.getMobile());
        shippingCardAddressResp.setPostal_code(return_address.getPostal_code());
        shippingCardAddressResp.setFirst_name(return_address.getFirst_name());
        return shippingCardAddressResp;
    }

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
}
