package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Component extends Base{   // 这个是 下单的时候， 从 product 中生成 的


    private long royaltyRuleId;
    private int royaltyRuleCount;

    @Enumerated(EnumType.STRING)
    private EnumCompoentGroupingType groupingType = EnumCompoentGroupingType.single;


    @Enumerated(EnumType.STRING)
    @NotNull
    private EnumProductComponentSource source;


    @Enumerated(EnumType.STRING)
    @NotNull
    private EnumValidateWay validate_way;
    private int royaltyAmount;
    @Enumerated(EnumType.STRING)
    private EnumRoyaltyRuleCategory royalty_mode;

    @Enumerated(EnumType.STRING)
    private EnumRoyaltyCollection_method collection_method;
    private long subscription;
    private Long ratePlan;
    private String reference;
    private String code;

    public EnumProductComponentSource getSource() {
        return source;
    }

    public void setSource(EnumProductComponentSource source) {
        this.source = source;
    }

    @NotNull
    private Long supplier;
    private long recipient;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EnumDuration duration;
    private int royaltyPercent;
    private long priceingType;


    private Boolean active = true;



    private EnumBillRecurringInterval billRecurringInterval = EnumBillRecurringInterval.day;
    private Integer billRecurringInterval_count;

    public Integer getBillRecurringInterval_count() {
        return billRecurringInterval_count;
    }

    public void setBillRecurringInterval_count(Integer billRecurringInterval_count) {
        this.billRecurringInterval_count = billRecurringInterval_count;
    }
    @Enumerated(EnumType.STRING)
    private EnumbillingScheme billingScheme = EnumbillingScheme.per_unit;
    private Long billing_unit_amount = 0l;
    private LocalDateTime billing_cycle_anchor;

    private String end_behavior;

    public LocalDateTime getBilling_cycle_anchor() {
        return billing_cycle_anchor;
    }

    public void setBilling_cycle_anchor(LocalDateTime billing_cycle_anchor) {
        this.billing_cycle_anchor = billing_cycle_anchor;
    }

    public EnumBillRecurringInterval getBillRecurringInterval() {
        return billRecurringInterval;
    }

    public void setBillRecurringInterval(EnumBillRecurringInterval billRecurringInterval) {
        this.billRecurringInterval = billRecurringInterval;
    }

    public EnumbillingScheme getBillingScheme() {
        return billingScheme;
    }

    public void setBillingScheme(EnumbillingScheme billingScheme) {
        this.billingScheme = billingScheme;
    }

    public Long getBilling_unit_amount() {
        return billing_unit_amount;
    }

    public void setBilling_unit_amount(Long billing_unit_amount) {
        this.billing_unit_amount = billing_unit_amount;
    }


    @Transient
    List<RatePlan> ratePlans;

    private long supplierId;

    private long product;
    private long componentRightId;

    public long getProduct() {
        return product;
    }

    public void setProduct(long productId) {
        this.product = productId;
    }

    public long getComponentRightId() {
        return componentRightId;
    }

    public void setComponentRight(long componentRightId) {
        this.componentRightId = componentRightId;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplier(long supplierId) {
        this.supplierId = supplierId;
    }

    @Transient
    private RoyaltyRule royaltyRule ;

    public RoyaltyRule getRoyaltyRule() {
        return royaltyRule;
    }

    public void setRoyaltyRule(RoyaltyRule royaltyRule) {
        this.royaltyRule = royaltyRule;
    }



    private String name;
    private String note;

    @Transient
    private AccessValidator accessValidator ;
    private long accessValidatorId;

    public long getAccessValidatorId() {
        return accessValidatorId;
    }

    public void setAccessValidatorId(long accessValidatorId) {
        this.accessValidatorId = accessValidatorId;
    }

    @Transient
    List<ComponentVounch> componentVounches;


    public List<ComponentVounch> getComponentRightVounches() {
        return componentVounches;
    }

    public void setComponentRightVounches(List<ComponentVounch> componentVounches) {
        this.componentVounches = componentVounches;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public AccessValidator getAccessValidator() {
        return accessValidator;
    }

    public void setAccessValidator(AccessValidator accessValidator) {
        this.accessValidator = accessValidator;
    }




    @NotNull
    private Long unit_off;

    public Long getUnit_off() {
        return unit_off;
    }

    public void setUnit_off(Long unit_off) {
        this.unit_off = unit_off;
    }

    public void setRoyaltyRuleCount(int royaltyRuleCount) {

        this.royaltyRuleCount = royaltyRuleCount;
    }

    public int getRoyaltyRuleCount() {
        return royaltyRuleCount;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public Long getSupplier() {
        return supplier;
    }

    public void setRecipient(long recipient) {
        this.recipient = recipient;
    }

    public long getRecipient() {
        return recipient;
    }

    public void setDuration(EnumDuration duration) {
        this.duration = duration;
    }

    public EnumDuration getDuration() {
        return duration;
    }

    public void setRoyaltyPercent(int royaltyPercent) {
        this.royaltyPercent = royaltyPercent;
    }

    public int getRoyaltyPercent() {
        return royaltyPercent;
    }

    public void setPriceingType(long priceingType) {
        this.priceingType = priceingType;
    }

    public long getPriceingType() {
        return priceingType;
    }


    public void setValidate_way(EnumValidateWay validate_way) {
        this.validate_way = validate_way;
    }

    public EnumValidateWay getValidate_way() {
        return validate_way;
    }

    public int getRoyaltyAmount() {
        return royaltyAmount;
    }

    public void setRoyaltyAmount(int royaltyAmount) {
        this.royaltyAmount = royaltyAmount;
    }

    public EnumRoyaltyRuleCategory getRoyalty_mode() {
        return royalty_mode;
    }

    public void setRoyalty_mode(EnumRoyaltyRuleCategory royalty_mode) {
        this.royalty_mode = royalty_mode;
    }

    public EnumRoyaltyCollection_method getCollection_method() {
        return collection_method;
    }

    public void setCollection_method(EnumRoyaltyCollection_method collection_method) {
        this.collection_method = collection_method;
    }

    public void setSubscription(long subscription) {
        this.subscription = subscription;
    }

    public long getSubscription() {
        return subscription;
    }

    public Long getRatePlan() {
        return ratePlan;
    }

    public void setRatePlan(Long ratePlan) {
        this.ratePlan = ratePlan;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
