package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumBillingDay;
import com.lt.dom.otcenum.EnumBillingPeriods;
import com.lt.dom.otcenum.EnumChargeModel;
import com.lt.dom.otcenum.EnumListPriceBase;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class RatePlan {

    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id")
    private long id;


    private long component_right;

    private long recipient;

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







    private EnumChargeModel chargeModel;
    private int listPrice;
    private EnumListPriceBase listPriceBase = EnumListPriceBase.BillingPeriod;
    private EnumBillingPeriods billingPeriods ;

    private EnumBillingDay billingDay;

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

    public EnumBillingPeriods getBillingPeriods() {
        return billingPeriods;
    }

    public void setBillingPeriods(EnumBillingPeriods billingPeriods) {
        this.billingPeriods = billingPeriods;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EnumBillingDay getBillingDay() {
        return billingDay;
    }

    public void setBillingDay(EnumBillingDay billingDay) {
        this.billingDay = billingDay;
    }
}
