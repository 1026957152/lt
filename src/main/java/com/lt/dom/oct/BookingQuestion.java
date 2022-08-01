package com.lt.dom.oct;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BookingQuestion {

    @Id
    private long id;
    private boolean requireAnswer;
    private long productId;

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}
