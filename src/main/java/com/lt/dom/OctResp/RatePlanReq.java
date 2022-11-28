package com.lt.dom.OctResp;

import com.lt.dom.otcenum.*;

import java.time.LocalDate;


public class RatePlanReq {




    private String name;
    private String description;
    private LocalDate effectiveStartDate;
    private LocalDate effectiveEndDate;


    private Long partner;

    public Long getPartner() {
        return partner;
    }

    public void setPartner(Long partner) {
        this.partner = partner;
    }

    private EnumChargeModel chargeModel;


    private EnumRatePlaneCommissionType commissionType;

    public EnumRatePlaneCommissionType getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(EnumRatePlaneCommissionType commissionType) {
        this.commissionType = commissionType;
    }

    private int listPrice;
    private EnumListPriceBase listPriceBase = EnumListPriceBase.BillingPeriod;
    private EnumBillingPeriod billingPeriods ;

    private EnumBillingCycleDayType billingDay;

    private int billCycleDay;

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
}
