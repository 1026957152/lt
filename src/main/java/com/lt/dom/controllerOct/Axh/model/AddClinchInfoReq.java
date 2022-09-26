package com.lt.dom.controllerOct.Axh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class AddClinchInfoReq {

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    //@JsonProperty("attachment")  //放款证明或其他补充资料:（返回信息）
    private String attachment放款证明或其他补充资料;


    //@JsonProperty("businessType")
    private Integer businessType_业务类型; //业务类型
    //@JsonProperty("clinchGuarantyWay")
    private Integer clinchGuarantyWay_担保方式; //担保方式




  //  @JsonProperty("clinchLoanAmount")
    @NotNull
    private Integer clinchLoanAmount_实际放款金额; // 实际放款金额(万元)：
    //@JsonProperty("clinchLoanLimit")
    @NotNull
    private Integer clinchLoanLimit_实际贷款期限; //实际贷款期限(个月)
    //@JsonProperty("clinchLoanRate")
    @NotNull
    private Integer clinchLoanRate_贷款年化利率; //贷款年化利率(%)：
    //@JsonProperty("clinchPayWay")
  //  @NotNull
    private Integer clinchPayWay_还款方式; //还款方式


    //@JsonProperty("clinchTime")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")

    private LocalDate clinchTime_成交时间;  //成交时间： yyyy-MM-dd



    @NotNull
    @JsonProperty("otherFee")
    private Float otherFee_担保服务费 = 0f; //担保服务费(万元)

    public String getAttachment放款证明或其他补充资料() {
        return attachment放款证明或其他补充资料;
    }

    public void setAttachment放款证明或其他补充资料(String attachment放款证明或其他补充资料) {
        this.attachment放款证明或其他补充资料 = attachment放款证明或其他补充资料;
    }

    public Integer getBusinessType_业务类型() {
        return businessType_业务类型;
    }

    public void setBusinessType_业务类型(Integer businessType_业务类型) {
        this.businessType_业务类型 = businessType_业务类型;
    }

    public Integer getClinchGuarantyWay_担保方式() {
        return clinchGuarantyWay_担保方式;
    }

    public void setClinchGuarantyWay_担保方式(Integer clinchGuarantyWay_担保方式) {
        this.clinchGuarantyWay_担保方式 = clinchGuarantyWay_担保方式;
    }

    public Integer getClinchLoanAmount_实际放款金额() {
        return clinchLoanAmount_实际放款金额;
    }

    public void setClinchLoanAmount_实际放款金额(Integer clinchLoanAmount_实际放款金额) {
        this.clinchLoanAmount_实际放款金额 = clinchLoanAmount_实际放款金额;
    }

    public Integer getClinchLoanLimit_实际贷款期限() {
        return clinchLoanLimit_实际贷款期限;
    }

    public void setClinchLoanLimit_实际贷款期限(Integer clinchLoanLimit_实际贷款期限) {
        this.clinchLoanLimit_实际贷款期限 = clinchLoanLimit_实际贷款期限;
    }

    public Integer getClinchLoanRate_贷款年化利率() {
        return clinchLoanRate_贷款年化利率;
    }

    public void setClinchLoanRate_贷款年化利率(Integer clinchLoanRate_贷款年化利率) {
        this.clinchLoanRate_贷款年化利率 = clinchLoanRate_贷款年化利率;
    }

    public Integer getClinchPayWay_还款方式() {
        return clinchPayWay_还款方式;
    }

    public void setClinchPayWay_还款方式(Integer clinchPayWay_还款方式) {
        this.clinchPayWay_还款方式 = clinchPayWay_还款方式;
    }

    public LocalDate getClinchTime_成交时间() {
        return clinchTime_成交时间;
    }

    public void setClinchTime_成交时间(LocalDate clinchTime_成交时间) {
        this.clinchTime_成交时间 = clinchTime_成交时间;
    }

    public Float getOtherFee_担保服务费() {
        return otherFee_担保服务费;
    }

    public void setOtherFee_担保服务费(Float otherFee_担保服务费) {
        this.otherFee_担保服务费 = otherFee_担保服务费;
    }
}
