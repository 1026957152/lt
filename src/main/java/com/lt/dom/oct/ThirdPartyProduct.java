package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class ThirdPartyProduct extends BaseWithoutId{

    @EmbeddedId
    ThirdPartyProductKey id;


    @JsonBackReference
    @ManyToOne
    @MapsId("thirdPartyId")
    @JoinColumn(name = "third_party_id")
    ThirdParty thirdParty;

    @JsonBackReference
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;


    public ThirdParty getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(ThirdParty thirdParty) {
        this.thirdParty = thirdParty;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
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


    public void setId(ThirdPartyProductKey id) {
        this.id = id;
    }

    public ThirdPartyProductKey getId() {
        return id;
    }
}
