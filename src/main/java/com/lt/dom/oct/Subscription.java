package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class Subscription extends Base {   // 这个是 下单的时候， 从 product 中生成 的

    private long royaltyRuleId;
    private int royaltyRuleCount;


    private EnumCompoentGroupingType groupingType = EnumCompoentGroupingType.single;




    private int royaltyAmount;
    private EnumRoyaltyRuleCategory royalty_mode;
    private EnumRoyaltyCollection_method collection_method;
    private EnumSubscriptionStatus status;
    private String code;
    private LocalDateTime current_period_end;
    private LocalDateTime current_period_start;
    private EnumTermSetting termSetting;
    private long ratePlan;



    @NotNull
    private Long supplier;
    private long recipient;


    private int royaltyPercent;
    private long priceingType;


    private Boolean active = true;



    private EnumBillRecurringInterval billRecurringInterval = EnumBillRecurringInterval.day;
    private EnumbillingScheme billingScheme = EnumbillingScheme.per_unit;
    private Long billing_unit_amount = 0l;
    private LocalDateTime billing_cycle_anchor;

    private String end_behavior;

    @NotNull
    private Long customer;
    private String description;

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

    public void setStatus(EnumSubscriptionStatus status) {
        this.status = status;
    }

    public EnumSubscriptionStatus getStatus() {
        return status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCurrent_period_end(LocalDateTime current_period_end) {
        this.current_period_end = current_period_end;
    }

    public LocalDateTime getCurrent_period_end() {
        return current_period_end;
    }

    public void setCurrent_period_start(LocalDateTime current_period_start) {
        this.current_period_start = current_period_start;
    }

    public LocalDateTime getCurrent_period_start() {
        return current_period_start;
    }

    public void setTermSetting(EnumTermSetting termSetting) {

        this.termSetting = termSetting;
    }

    public EnumTermSetting getTermSetting() {
        return termSetting;
    }

    public void setRatePlan(long ratePlan) {
        this.ratePlan = ratePlan;
    }

    public long getRatePlan() {
        return ratePlan;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    public Long getCustomer() {
        return customer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
