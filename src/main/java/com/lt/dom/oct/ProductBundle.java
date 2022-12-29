package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.otcenum.EnumPassDorationUnit;
import com.lt.dom.otcenum.EnumProductComponentSource;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ProductBundle extends Base{



    @JsonBackReference
    @ManyToOne
    //@MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;
    private Long burdle;

    @Enumerated(EnumType.STRING)
    private EnumProductComponentSource burdleProductSource;
    private String productCode;

    @Transient
    private AgentProduct agentProduct;

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

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setAgentProduct(AgentProduct agentProduct) {
        this.agentProduct = agentProduct;
    }

    public AgentProduct getAgentProduct() {
        return agentProduct;
    }


// https://www.giftrocket.com/docs
}
