package com.lt.dom.oct;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CrossSellKey implements Serializable {

    @Column(name = "product_id")
    Long productId;

    @Column(name = "cross_sell_product_id")
    Long crossSellProductId;

    public CrossSellKey(long id, long id1) {
        this.productId = id;
        this.crossSellProductId = id1;
    }

    public Long getCrossSellProductId() {
        return crossSellProductId;
    }

    public void setCrossSellProductId(Long crossSellProductId) {
        this.crossSellProductId = crossSellProductId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public CrossSellKey() {

    }
}