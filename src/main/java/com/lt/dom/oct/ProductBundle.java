package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.otcenum.EnumPassDorationUnit;
import com.lt.dom.otcenum.EnumProductComponentSource;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.time.LocalDateTime;

@Entity
public class ProductBundle extends Base{



    @JsonBackReference
    @ManyToOne
    //@MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;
    private Long burdle;
    private EnumProductComponentSource burdleProductSource;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public void setBurdle(Long burdle) {
        this.burdle = burdle;
    }

    public Long getBurdle() {
        return burdle;
    }

    public void setBurdleProductSource(EnumProductComponentSource burdleProductSource) {
        this.burdleProductSource = burdleProductSource;
    }

    public EnumProductComponentSource getBurdleProductSource() {
        return burdleProductSource;
    }


// https://www.giftrocket.com/docs
}
