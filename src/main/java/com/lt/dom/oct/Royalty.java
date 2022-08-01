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
    private long componentRightId;
    private long componentId;




    private long source_no; //关联对象的 order_no。
    private long source_user; //分润接收方 user 对象的 id。
    private int amount;
    private long royaltySettlementId;
    private long settleAccount;


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

    public void setComponentRightId(long componentRightId) {
        this.componentRightId = componentRightId;
    }

    public long getComponentRightId() {
        return componentRightId;
    }

    public void setComponentId(long componentId) {
        this.componentId = componentId;
    }

    public long getComponentId() {
        return componentId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setRoyaltySettlementId(long royaltySettlementId) {
        this.royaltySettlementId = royaltySettlementId;
    }

    public long getRoyaltySettlementId() {
        return royaltySettlementId;
    }

    public void setSettleAccount(long settleAccount) {
        this.settleAccount = settleAccount;
    }

    public long getSettleAccount() {
        return settleAccount;
    }
}
