package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumThirdParty;
import com.lt.dom.otcenum.EnumThirdPartyStatus;

import javax.persistence.*;
import java.util.List;

@Entity
public class ThirdParty extends Base{




    @OneToMany(mappedBy="thirdParty",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ThirdPartyProduct> products;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY,    cascade = CascadeType.ALL)
    List<ThirdPartyProduct> thirdPartyProducts;

    @Enumerated(EnumType.STRING)
    private EnumThirdParty platform;
    @Enumerated(EnumType.STRING)
    private EnumThirdPartyStatus status;
    private String code;
    private String baseURL;

    private String partner_id;
    private String authorization_code;

    public List<ThirdPartyProduct> getThirdPartyProducts() {
        return thirdPartyProducts;
    }

    public void setThirdPartyProducts(List<ThirdPartyProduct> thirdPartyProducts) {
        this.thirdPartyProducts = thirdPartyProducts;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public EnumThirdPartyStatus getStatus() {
        return status;
    }

    public void setStatus(EnumThirdPartyStatus status) {
        this.status = status;
    }

    public void addProductInList(ThirdPartyProduct addst) {
        thirdPartyProducts.add(addst);
    }

    private Long supplier;

    public List<ThirdPartyProduct> getProducts() {
        return products;
    }

    public void setProducts(List<ThirdPartyProduct> products) {
        this.products = products;
    }


    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getAuthorization_code() {
        return authorization_code;
    }

    public void setAuthorization_code(String authorization_code) {
        this.authorization_code = authorization_code;
    }

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

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public Long getSupplier() {
        return supplier;
    }

    public EnumThirdParty getPlatform() {
        return platform;
    }

    public void setPlatform(EnumThirdParty platform) {
        this.platform = platform;
    }
}
