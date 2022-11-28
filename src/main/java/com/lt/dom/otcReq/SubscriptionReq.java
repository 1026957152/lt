package com.lt.dom.otcReq;

import com.lt.dom.OctResp.RatePlanResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class SubscriptionReq {   // 这个是 下单的时候， 从 product 中生成 的


    private long royaltyRuleId;
    private int Initial_Term_Period_Type;


    private EnumCompoentGroupingType groupingType = EnumCompoentGroupingType.single;





    private EnumRoyaltyCollection_method collection_method;
    private EnumSubscriptionStatus status;
    private String code;
    private LocalDateTime current_period_end;
    private LocalDateTime current_period_start;
 //   @NotNull
    private Long ratePlan;




    private int royaltyPercent;
    private long priceingType;


    private Boolean active = true;


    private EnumTermSetting termSetting = EnumTermSetting.Termed;
    private String description;

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



    private long product;



    public long getProduct() {
        return product;
    }

    public void setProduct(long productId) {
        this.product = productId;
    }




    private String name;
    private String note;



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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
