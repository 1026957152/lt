package com.lt.dom.OctResp;

import com.lt.dom.oct.Product;
import com.lt.dom.oct.RatePlan;
import com.lt.dom.otcenum.*;

import java.time.LocalDate;


public class RatePlanResp extends BaseResp{




    private String name;
    private String description;
    private LocalDate effectiveStartDate;
    private LocalDate effectiveEndDate;







    private EnumChargeModel chargeModel;


    private EnumRatePlaneCommissionType commissionType;
    private String code;
    private String productCode;
    private String productName;

    public static RatePlanResp from(RatePlan e) {
        RatePlanResp ratePlanResp = new RatePlanResp();
        ratePlanResp.setBillCycleDay(e.getBillCycleDay()); // 那一天计算结算
        ratePlanResp.setBillingDay(e.getBillingDay()); //   这两个决定那一天
        ratePlanResp.setBillingPeriods(e.getBillingPeriods()); //月，年，环视 星期
        ratePlanResp.setChargeModel(e.getChargeModel());
        ratePlanResp.setListPrice(e.getListPrice());
        ratePlanResp.setEffectiveEndDate(e.getEffectiveEndDate());
        ratePlanResp.setEffectiveStartDate(e.getEffectiveStartDate());
        ratePlanResp.setListPriceBase(e.getListPriceBase());
        ratePlanResp.setCommissionType(e.getCommissionType());
        ratePlanResp.setCreatedDate(e.getModifiedDate());
        ratePlanResp.setModifiedDate(e.getCreatedDate());
        ratePlanResp.setCode(e.getCode());
        return ratePlanResp;
    }

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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void withProduct(Product product) {

        this.setProductCode(product.getCode());
        this.setProductName(product.getName());

    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }
}
