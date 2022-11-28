package com.lt.dom.controllerOct.Axh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.enum_.EnumClinchPayWay还款方式;
import com.lt.dom.otcenum.enum_.EnumGuarantyWay担保方式;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreditWaitConfirmReq {

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }




    @NotNull
    @JsonProperty("creditAmount_授信额度")
    private Float creditAmount_授信额度; // 授信额度(万元)：

    @NotNull
    @JsonProperty("endTime_授信额度有效期至")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime_授信额度有效期至;  // 授信额度有效期至：yyyy-MM-dd




    @JsonProperty("clinchLoanRate_贷款年化利率")
    @NotNull
    private Float clinchLoanRate_贷款年化利率; //贷款年化利率(%)：

    @NotNull
    @JsonProperty("clinchLoanLimit_贷款期限")

    private Integer clinchLoanLimit_贷款期限; //实际贷款期限(个月)
    @JsonProperty("clinchPayWay_还款方式")
    //  @NotNull
    private EnumClinchPayWay还款方式 clinchPayWay_还款方式; //还款方式



    @JsonProperty("clinchGuarantyWay_担保方式")
    private EnumGuarantyWay担保方式 clinchGuarantyWay_担保方式; //担保方式

    public Float getClinchLoanRate_贷款年化利率() {
        return clinchLoanRate_贷款年化利率;
    }

    public void setClinchLoanRate_贷款年化利率(Float clinchLoanRate_贷款年化利率) {
        this.clinchLoanRate_贷款年化利率 = clinchLoanRate_贷款年化利率;
    }

    public EnumClinchPayWay还款方式 getClinchPayWay_还款方式() {
        return clinchPayWay_还款方式;
    }

    public void setClinchPayWay_还款方式(EnumClinchPayWay还款方式 clinchPayWay_还款方式) {
        this.clinchPayWay_还款方式 = clinchPayWay_还款方式;
    }

    public EnumGuarantyWay担保方式 getClinchGuarantyWay_担保方式() {
        return clinchGuarantyWay_担保方式;
    }

    public void setClinchGuarantyWay_担保方式(EnumGuarantyWay担保方式 clinchGuarantyWay_担保方式) {
        this.clinchGuarantyWay_担保方式 = clinchGuarantyWay_担保方式;
    }

    public Float getCreditAmount_授信额度() {
        return creditAmount_授信额度;
    }

    public void setCreditAmount_授信额度(Float creditAmount_授信额度) {
        this.creditAmount_授信额度 = creditAmount_授信额度;
    }

    public LocalDate getEndTime_授信额度有效期至() {
        return endTime_授信额度有效期至;
    }

    public void setEndTime_授信额度有效期至(LocalDate endTime_授信额度有效期至) {
        this.endTime_授信额度有效期至 = endTime_授信额度有效期至;
    }

    public Integer getClinchLoanLimit_贷款期限() {
        return clinchLoanLimit_贷款期限;
    }

    public void setClinchLoanLimit_贷款期限(Integer clinchLoanLimit_贷款期限) {
        this.clinchLoanLimit_贷款期限 = clinchLoanLimit_贷款期限;
    }
}
