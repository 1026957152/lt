package com.lt.dom.oct;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Partner {

    @Id
    private long id;
    private long supplierId;
    private long partnerId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(long partnerId) {
        this.partnerId = partnerId;
    }
}
