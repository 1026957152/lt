package com.lt.dom.oct;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ThirdPartyProductKey implements Serializable {

    @Column(name = "third_party_id")
    Long thirdPartyId;

    @Column(name = "product_id")
    Long productId;

    public ThirdPartyProductKey(long id, long id1) {
        this.thirdPartyId = id;
        this.productId = id1;
    }

    public ThirdPartyProductKey() {

    }

    public Long getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(Long thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}