package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumBillingPeriods;
import com.lt.dom.otcenum.EnumChargeModel;
import com.lt.dom.otcenum.EnumListPriceBase;

import java.time.LocalDate;

public class RatePlan {

    private long component_right;

    private long recipient;

    public long getRecipient() {
        return recipient;
    }

    public void setRecipient(long recipient) {
        this.recipient = recipient;
    }
    private LocalDate EffectiveStartDate;

    private LocalDate EffectiveEndDate;

    private EnumChargeModel chargeModel;



    public LocalDate getEffectiveStartDate() {
        return EffectiveStartDate;
    }

    public void setEffectiveStartDate(LocalDate effectiveStartDate) {
        EffectiveStartDate = effectiveStartDate;
    }

    public LocalDate getEffectiveEndDate() {
        return EffectiveEndDate;
    }

    public void setEffectiveEndDate(LocalDate effectiveEndDate) {
        EffectiveEndDate = effectiveEndDate;
    }

    private int listPrice;

    private EnumListPriceBase listPriceBase = EnumListPriceBase.BillingPeriod;

    private EnumBillingPeriods billingPeriods ;

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

    public EnumBillingPeriods getBillingPeriods() {
        return billingPeriods;
    }

    public void setBillingPeriods(EnumBillingPeriods billingPeriods) {
        this.billingPeriods = billingPeriods;
    }
}
