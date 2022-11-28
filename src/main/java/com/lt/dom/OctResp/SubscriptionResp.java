package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionResp extends BaseResp {   // 这个是 下单的时候， 从 product 中生成 的


    private long royaltyRuleId;
    private int Initial_Term_Period_Type;


    private EnumCompoentGroupingType groupingType = EnumCompoentGroupingType.single;



   // @NotNull
    private EnumProductComponentSource source;

    private EnumRoyaltyCollection_method collection_method;
    private EnumSubscriptionStatus status;
    private String code;
    private LocalDateTime current_period_end;
    private LocalDateTime current_period_start;
 //   @NotNull
    private Long ratePlan;
    private Long customer;

    public static SubscriptionResp from(Subscription subscription) {
        SubscriptionResp subscriptionResp = new SubscriptionResp();
        subscriptionResp.setCode(subscription.getCode());
        subscriptionResp.setName(subscription.getName());
        subscriptionResp.setBillingScheme(subscription.getBillingScheme());
        subscriptionResp.setBilling_cycle_anchor(subscription.getBilling_cycle_anchor());
        subscriptionResp.setComponentRight(subscription.getComponentRightId());
        subscriptionResp.setBillRecurringInterval(subscription.getBillRecurringInterval());
        subscriptionResp.setCurrent_period_end(subscription.getCurrent_period_end());
        subscriptionResp.setCurrent_period_start(subscription.getCurrent_period_start());

        subscriptionResp.setRatePlan(subscription.getRatePlan());

        subscriptionResp.setProduct(subscription.getProduct());
        subscriptionResp.setCustomer(subscription.getCustomer());

        subscriptionResp.setCreatedDate(subscription.getCreatedDate());

        subscriptionResp.setModifiedDate(subscription.getModifiedDate());

        return subscriptionResp;
    }
    public static void withRateplans(SubscriptionResp subscription,List<RatePlan> ratePlans) {


        subscription.setRatePlans(ratePlans.stream().map(e->{
            RatePlanResp ratePlanResp = RatePlanResp.from(e);
            return ratePlanResp;

        }).collect(Collectors.toList()));

    }


    public EnumProductComponentSource getSource() {
        return source;
    }

    public void setSource(EnumProductComponentSource source) {
        this.source = source;
    }

    @NotNull
    private Long supplier;
    private long recipient;

    @NotNull
    private EnumDuration duration;
    private int royaltyPercent;
    private long priceingType;


    private Boolean active = true;


    private EnumTermSetting termSetting = EnumTermSetting.Termed;

    public EnumTermSetting getTermSetting() {
        return termSetting;
    }

    public void setTermSetting(EnumTermSetting termSetting) {
        this.termSetting = termSetting;
    }

    private EnumBillRecurringInterval billRecurringInterval = EnumBillRecurringInterval.day;
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



    List<RatePlanResp> ratePlans;

    private long supplierId;

    private long product;

    @NotNull
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

    public Long getRatePlan() {
        return ratePlan;
    }

    public void setRatePlan(Long ratePlan) {
        this.ratePlan = ratePlan;
    }

    public <R> void setRatePlans(List<RatePlanResp> ratePlans) {
        this.ratePlans = ratePlans;
    }

    public List<RatePlanResp> getRatePlans() {
        return ratePlans;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    public Long getCustomer() {
        return customer;
    }
}
