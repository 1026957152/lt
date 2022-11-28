package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.ThirdParty;
import com.lt.dom.oct.ThirdPartyProduct;
import com.lt.dom.oct.ThirdPartyProductKey;

import javax.persistence.*;
import java.time.LocalDateTime;

public class ThirdPartyProductResp extends BaseResp{


    ThirdPartyResp thirdParty;


    ProductResp product;

    public static ThirdPartyProductResp simpleFrom(ThirdPartyProduct e) {
        ThirdPartyProductResp thirdPartyProductResp = new ThirdPartyProductResp();
        thirdPartyProductResp.setProduct(ProductResp.Simplefrom(e.getProduct()));
        thirdPartyProductResp.setThirdParty(ThirdPartyResp.from(e.getThirdParty()));
        thirdPartyProductResp.setCreatedDate(e.getCreatedDate());
        thirdPartyProductResp.setModifiedDate(e.getModifiedDate());
        return thirdPartyProductResp;

    }


    public ThirdPartyResp getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(ThirdPartyResp thirdParty) {
        this.thirdParty = thirdParty;
    }

    public ProductResp getProduct() {
        return product;
    }

    public void setProduct(ProductResp product) {
        this.product = product;
    }

    private String code;

    private String name;

    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
