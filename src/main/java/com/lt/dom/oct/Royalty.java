package com.lt.dom.oct;

import com.lt.dom.domain.SettleAccount;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;


@Entity
public class Royalty {  //分润模板

    //一级，二级，特定;
    @Id
    private long id;


    private String type; //    //供应商 ，平台，分销一级，分销二级，特定


    private long productId;
    private long supplierId;

    private long parentProductId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public long getParentProductId() {
        return parentProductId;
    }

    public void setParentProductId(long parentProductId) {
        this.parentProductId = parentProductId;
    }

    @Transient
    private RoyaltyRule royaltyRule;

    @Transient
    private SettleAccount settle_account;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SettleAccount getSettle_account() {
        return settle_account;
    }

    public void setSettle_account(SettleAccount settle_account) {
        this.settle_account = settle_account;
    }
}
