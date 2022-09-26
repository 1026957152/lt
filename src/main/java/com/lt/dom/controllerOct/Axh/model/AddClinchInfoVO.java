package com.lt.dom.controllerOct.Axh.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;



@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddClinchInfoVO {
/*
    {
        "attachment": "",   放款证明或其他补充资料:（返回信息）
        "backMoneyTypeDisplay": "",
            "businessType": "",   业务类型
        "clinchGuarantyWay": "",   担保方式：
        "clinchGuarantyWayDisplay": "",
            "clinchLoanAmount": 0,   实际放款金额(万元)：
        "clinchLoanLimit": 0,   实际贷款期限(个月)：
        "clinchLoanRate": 0,   贷款年化利率(%)：
        "clinchPayWay": "",   还款方式：
        "clinchTime": "",    成交时间： yyyy-MM-dd
        "createBy": "",
            "createTime": "",
            "creditType": "",
            "disabled": 0,
            "entId": 0,    企业ID
        "guarantySubEnt": "",   担保主体企业：
        "id": 0,
            "instId": 0,    机构id
        "instName": "",
            "orderId": 0,   申请id
        "otherFee": 0,   担保服务费(万元)：
        "tenantId": 0,
            "updateBy": "",
            "updateTime": "",
            "userId": 0
    }*/
    @JsonProperty("attachment")  //放款证明或其他补充资料:（返回信息）
    private String attachment放款证明或其他补充资料;
    @JsonProperty("backMoneyTypeDisplay")
    private String backMoneyTypeDisplay;
    @JsonProperty("businessType")
    private Integer businessType_业务类型; //业务类型
    @JsonProperty("clinchGuarantyWay")
    private Integer clinchGuarantyWay_担保方式; //担保方式
    @JsonProperty("clinchGuarantyWayDisplay")
    private String clinchGuarantyWayDisplay;
    @JsonProperty("clinchLoanAmount")
    private Integer clinchLoanAmount_实际放款金额; // 实际放款金额(万元)：
    @JsonProperty("clinchLoanLimit")
    private Integer clinchLoanLimit_实际贷款期限; //实际贷款期限(个月)
    @JsonProperty("clinchLoanRate")
    private Integer clinchLoanRate_贷款年化利率; //贷款年化利率(%)：
    @JsonProperty("clinchPayWay")
    private Integer clinchPayWay_还款方式; //还款方式
    @JsonProperty("clinchTime")
    private String clinchTime_成交时间;  //成交时间： yyyy-MM-dd
    @JsonProperty("createBy")
    private String createBy;
    @JsonProperty("createTime")
    private String createTime;
    @JsonProperty("creditType")
    private String creditType;
    @JsonProperty("disabled")
    private Integer disabled;
    @JsonProperty("entId")
    private Integer entId_企业ID;
    @JsonProperty("guarantySubEnt")
    private String guarantySubEnt_担保主体企业;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("instId")
    private Integer instId_机构id; //机构id
    @JsonProperty("instName")
    private String instName;
    @JsonProperty("orderId")
    private Integer orderId_申请id; // 申请id
    @JsonProperty("otherFee")
    private Integer otherFee_担保服务费; //担保服务费(万元)
    @JsonProperty("tenantId")
    private Integer tenantId;
    @JsonProperty("updateBy")
    private String updateBy;
    @JsonProperty("updateTime")
    private String updateTime;
    @JsonProperty("userId")
    private Integer userId;

    public String getAttachment放款证明或其他补充资料() {
        return attachment放款证明或其他补充资料;
    }

    public void setAttachment放款证明或其他补充资料(String attachment放款证明或其他补充资料) {
        this.attachment放款证明或其他补充资料 = attachment放款证明或其他补充资料;
    }

    public String getBackMoneyTypeDisplay() {
        return backMoneyTypeDisplay;
    }

    public void setBackMoneyTypeDisplay(String backMoneyTypeDisplay) {
        this.backMoneyTypeDisplay = backMoneyTypeDisplay;
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

    public String getClinchGuarantyWayDisplay() {
        return clinchGuarantyWayDisplay;
    }

    public void setClinchGuarantyWayDisplay(String clinchGuarantyWayDisplay) {
        this.clinchGuarantyWayDisplay = clinchGuarantyWayDisplay;
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

    public String getClinchTime_成交时间() {
        return clinchTime_成交时间;
    }

    public void setClinchTime_成交时间(String clinchTime_成交时间) {
        this.clinchTime_成交时间 = clinchTime_成交时间;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreditType() {
        return creditType;
    }

    public void setCreditType(String creditType) {
        this.creditType = creditType;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public Integer getEntId_企业ID() {
        return entId_企业ID;
    }

    public void setEntId_企业ID(Integer entId_企业ID) {
        this.entId_企业ID = entId_企业ID;
    }

    public String getGuarantySubEnt_担保主体企业() {
        return guarantySubEnt_担保主体企业;
    }

    public void setGuarantySubEnt_担保主体企业(String guarantySubEnt_担保主体企业) {
        this.guarantySubEnt_担保主体企业 = guarantySubEnt_担保主体企业;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInstId_机构id() {
        return instId_机构id;
    }

    public void setInstId_机构id(Integer instId_机构id) {
        this.instId_机构id = instId_机构id;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public Integer getOrderId_申请id() {
        return orderId_申请id;
    }

    public void setOrderId_申请id(Integer orderId_申请id) {
        this.orderId_申请id = orderId_申请id;
    }

    public Integer getOtherFee_担保服务费() {
        return otherFee_担保服务费;
    }

    public void setOtherFee_担保服务费(Integer otherFee_担保服务费) {
        this.otherFee_担保服务费 = otherFee_担保服务费;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
