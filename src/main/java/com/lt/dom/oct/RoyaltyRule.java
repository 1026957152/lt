package com.lt.dom.oct;

import com.lt.dom.otcenum.*;

import javax.persistence.*;


@Entity
public class RoyaltyRule {

    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private long componentId;
    private long componentRight;
    private long productId;

    private long sourceId;
    private String sourceType;
    private String splitCode;
    private long settle_account;
    private long component;
    private Long supplier;
    private int royaltyPercent;

    private EnumRoyaltyCollection_method collection_method;
    private EnumBillRecurringInterval billRecurringInterval = EnumBillRecurringInterval.day;
    private EnumbillingScheme billingScheme = EnumbillingScheme.per_unit;

    private Long billing_unit_amount;



    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private EnumRoyaltyRuleCategory royalty_mode;
    private String refund_mode;
    private EnumAllocationMethod allocation_mode;

  //  private List<RoyaltyRuleData> royaltyRuleData;
    private long recipient;  //结算账号
    private int amount;
    private int percent;

    private EnumWhenSettle whenSettle;
    private EnumRoyaltyRuleCategory category;

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public EnumRoyaltyRuleCategory getCategory() {
        return category;
    }

    public void setCategory(EnumRoyaltyRuleCategory category) {
        this.category = category;
    }
/*
    public List<RoyaltyRuleData> getRoyaltyRuleData() {
        return royaltyRuleData;
    }

    public void setRoyaltyRuleData(List<RoyaltyRuleData> royaltyRuleData) {
        this.royaltyRuleData = royaltyRuleData;
    }
*/

    public long getRecipient() {
         return recipient;
    }

    public void setRecipient(long recipient) {
        this.recipient = recipient;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setComponentId(long componentId) {

        this.componentId = componentId;
    }

    public long getComponentId() {
        return componentId;
    }

    public void setComponentRight(long componentRightId) {
        this.componentRight = componentRightId;
    }

    public long getComponentRight() {
        return componentRight;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }

    public EnumWhenSettle getWhenSettle() {
        return whenSettle;
    }

    public void setWhenSettle(EnumWhenSettle whenSettle) {
        this.whenSettle = whenSettle;
    }



    private boolean active;//

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSplitCode(String splitCode) {
        this.splitCode = splitCode;
    }

    public String getSplitCode() {
        return splitCode;
    }

    public void setRoyalty_mode(EnumRoyaltyRuleCategory royalty_mode) {
        this.royalty_mode = royalty_mode;
    }

    public EnumRoyaltyRuleCategory getRoyalty_mode() {
        return royalty_mode;
    }

    public void setSettle_account(long settle_account) {
        this.settle_account = settle_account;
    }

    public long getSettle_account() {
        return settle_account;
    }

    public void setComponent(long component) {
        this.component = component;
    }

    public long getComponent() {
        return component;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public Long getSupplier() {
        return supplier;
    }

    public void setRoyaltyPercent(int royaltyPercent) {
        this.royaltyPercent = royaltyPercent;
    }

    public int getRoyaltyPercent() {
        return royaltyPercent;
    }

    public void setCollection_method(EnumRoyaltyCollection_method collection_method) {
        this.collection_method = collection_method;
    }

    public EnumRoyaltyCollection_method getCollection_method() {
        return collection_method;
    }

    /*      "royalty_mode": "rate",
              "refund_mode": "no_refund",
              "allocation_mode": "receipt_reserved",
              "data": [
    {
        "level": 0,
            "value": 11
    },
    {
        "level": 1,
            "value": 12
    }
        ]*/
}
