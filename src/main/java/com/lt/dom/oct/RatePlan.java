package com.lt.dom.oct;

import com.lt.dom.otcenum.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
//https://www.zuora.com/developer/api-reference/?_ga=2.81241876.28207374.1666457102-461931621.1664039613&_gl=1*1xfj59h*_ga*NDYxOTMxNjIxLjE2NjQwMzk2MTM.*_ga_MY8CQ650DH*MTY2NjQ2MjI2OS4yNC4xLjE2NjY0NjU5MDUuMC4wLjA.#operation/Object_POSTProductRatePlanCharge

@Entity
public class RatePlan extends Base{




    @OneToMany(mappedBy="ratePlan",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<PartnerShareRatePlan> sharePartners;

    public List<PartnerShareRatePlan> getSharePartners() {
        return sharePartners;
    }

    public void setSharePartners(List<PartnerShareRatePlan> sharePartners) {
        this.sharePartners = sharePartners;
    }

    private EnumUsageType usageType = EnumUsageType.metered ;
    private EnumPrivacyLevel privacyLevel;

    public EnumUsageType getUsageType() {
        return usageType;
    }

    public void setUsageType(EnumUsageType usageType) {
        this.usageType = usageType;
    }

    private long component_right;

    private long recipient;
    private long product;
    @Enumerated(EnumType.STRING)
    private EnumRatePlaneCommissionType commissionType;
    private long supplier;
    private String code;

    public long getRecipient() {
        return recipient;
    }

    public void setRecipient(long recipient) {
        this.recipient = recipient;
    }

    private String name;
    private String description;
    private LocalDate effectiveStartDate;
    private LocalDate effectiveEndDate;






    @Enumerated(EnumType.STRING)
    private EnumChargeModel chargeModel;
    private int listPrice;

    @Enumerated(EnumType.STRING)
    private EnumListPriceBase listPriceBase = EnumListPriceBase.BillingPeriod;

    @Enumerated(EnumType.STRING)
    private EnumBillingPeriod billingPeriods ;  // 按月，或者 直接 一个周期。

    @Enumerated(EnumType.STRING)
    private EnumBillingCycleDayType billingDay;// 和 bCD 确定是那一天  月某一天，周某一天，

    private int billCycleDay;  // 那一天

    public int getBillCycleDay() {
        return billCycleDay;
    }

    public void setBillCycleDay(int billCycleDay) {
        this.billCycleDay = billCycleDay;
    }

    public LocalDate getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(LocalDate effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public LocalDate getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(LocalDate effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }







    // Effective Start Date: Enter the day that your rate plan becomes available, in MM/DD/YYYY format. This is also when you can begin adding the rate plan to a subscription.
   // Effective End Date: Enter the date that your rate plan expires and can no longer be added to a subscription, in MM/DD/YYYY format.


    public EnumChargeModel getChargeModel() {
        return chargeModel;
    }

    public void setChargeModel(EnumChargeModel chargeModel) {
        this.chargeModel = chargeModel;
    }

    public int getListPrice() {
        return listPrice;
    }

    public void setListPrice(int listPrice) {
        this.listPrice = listPrice;
    }

    public EnumListPriceBase getListPriceBase() {
        return listPriceBase;
    }

    public void setListPriceBase(EnumListPriceBase listPriceBase) {
        this.listPriceBase = listPriceBase;
    }

    public EnumBillingPeriod getBillingPeriods() {
        return billingPeriods;
    }

    public void setBillingPeriods(EnumBillingPeriod billingPeriods) {
        this.billingPeriods = billingPeriods;
    }



    public EnumBillingCycleDayType getBillingDay() {
        return billingDay;
    }

    public void setBillingDay(EnumBillingCycleDayType billingDay) {
        this.billingDay = billingDay;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public long getProduct() {
        return product;
    }

    public void setCommissionType(EnumRatePlaneCommissionType commissionType) {
        this.commissionType = commissionType;
    }

    public EnumRatePlaneCommissionType getCommissionType() {
        return commissionType;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPrivacyLevel(EnumPrivacyLevel privacyLevel) {
        this.privacyLevel = privacyLevel;
    }

    public EnumPrivacyLevel getPrivacyLevel() {
        return privacyLevel;
    }
}
