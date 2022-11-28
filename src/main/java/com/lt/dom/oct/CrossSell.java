package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
public class CrossSell{

    @EmbeddedId
    CrossSellKey id;

    public CrossSellKey getId() {
        return id;
    }

    public void setId(CrossSellKey id) {
        this.id = id;
    }

    @JsonBackReference
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;



    @JsonBackReference
    @ManyToOne
    @MapsId("crossSellProductId")
    @JoinColumn(name = "cross_sell_product_id")
    Product crossSellProduct;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getCrossSellProduct() {
        return crossSellProduct;
    }

    public void setCrossSellProduct(Product crossSellProduct) {
        this.crossSellProduct = crossSellProduct;
    }

    private String code;
    private Long supplier;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getSupplier() {
        return supplier;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }



}
