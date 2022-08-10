package com.lt.dom.oct;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;


@Entity
public class Pickup {
    @Version
    private Integer version;
    @Id
    private long id;
    private String name;
    private long supplierId;

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
