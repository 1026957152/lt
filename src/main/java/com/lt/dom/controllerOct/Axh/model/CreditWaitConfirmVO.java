package com.lt.dom.controllerOct.Axh.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

///xydfinanceproductorderinfo/creditWaitConfirm

public class CreditWaitConfirmVO {

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    private String qrcode;

    public String getAttachment_上传返款证明返回信息() {
        return attachment_上传返款证明返回信息;
    }

    public void setAttachment_上传返款证明返回信息(String attachment_上传返款证明返回信息) {
        this.attachment_上传返款证明返回信息 = attachment_上传返款证明返回信息;
    }

    @JsonProperty("attachment")
    private String attachment_上传返款证明返回信息; //上传返款证明返回信息
    @JsonProperty("businessType")
    private Integer businessType_业务类型; //业务类型：直租、回租、其它
    @JsonProperty("createBy")
    private String createBy;
    @JsonProperty("createTime")
    private String createTime;
    @JsonProperty("creditAmount")
    private Integer creditAmount_授信额度; // 授信额度(万元)：
    @JsonProperty("disabled")
    private Integer disabled;
    @JsonProperty("endTime")
    private String endTime_授信额度有效期至;  // 授信额度有效期至：yyyy-MM-dd
    @JsonProperty("guarantyWay")
    private Integer guarantyWay_担保方式;  //担保方式 1信用 2保证 3质押 4抵押5其他
    @JsonProperty("guarantyWayDisplay")
    private String guarantyWayDisplay;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("instId")
    private Integer instId_机构id; //机构id
    @JsonProperty("instName")
    private String instName;
    @JsonProperty("loanLimit")
    private Integer loanLimit_贷款期限;  //贷款期限(个月)：
    @JsonProperty("loanRate")
    private Integer loanRate_贷款年化利率; //贷款年化利率(%)：
    @JsonProperty("orderId")
    private Integer orderId_申请id;  //申请id
    @JsonProperty("otherFee")
    private Integer otherFee_担保服务费; //担保服务费(万元)：
    @JsonProperty("payWay")
    private Integer payWay_还款方式;  //还款方式：1信用 2保证 3质押 4抵押 5其他
    @JsonProperty("payWayDisplay")
    private String payWayDisplay;
    @JsonProperty("startTime")
    private String startTime;
    @JsonProperty("status")
    private String status;
    @JsonProperty("tenantId")
    private Integer tenantId;
    @JsonProperty("updateBy")
    private String updateBy;
    @JsonProperty("updateTime")
    private String updateTime;
    @JsonProperty("userId")
    private Integer userId;

    public Integer getBusinessType_业务类型() {
        return businessType_业务类型;
    }

    public void setBusinessType_业务类型(Integer businessType_业务类型) {
        this.businessType_业务类型 = businessType_业务类型;
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

    public Integer getCreditAmount_授信额度() {
        return creditAmount_授信额度;
    }

    public void setCreditAmount_授信额度(Integer creditAmount_授信额度) {
        this.creditAmount_授信额度 = creditAmount_授信额度;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getEndTime_授信额度有效期至() {
        return endTime_授信额度有效期至;
    }

    public void setEndTime_授信额度有效期至(String endTime_授信额度有效期至) {
        this.endTime_授信额度有效期至 = endTime_授信额度有效期至;
    }

    public Integer getGuarantyWay_担保方式() {
        return guarantyWay_担保方式;
    }

    public void setGuarantyWay_担保方式(Integer guarantyWay_担保方式) {
        this.guarantyWay_担保方式 = guarantyWay_担保方式;
    }

    public String getGuarantyWayDisplay() {
        return guarantyWayDisplay;
    }

    public void setGuarantyWayDisplay(String guarantyWayDisplay) {
        this.guarantyWayDisplay = guarantyWayDisplay;
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

    public Integer getLoanLimit_贷款期限() {
        return loanLimit_贷款期限;
    }

    public void setLoanLimit_贷款期限(Integer loanLimit_贷款期限) {
        this.loanLimit_贷款期限 = loanLimit_贷款期限;
    }

    public Integer getLoanRate_贷款年化利率() {
        return loanRate_贷款年化利率;
    }

    public void setLoanRate_贷款年化利率(Integer loanRate_贷款年化利率) {
        this.loanRate_贷款年化利率 = loanRate_贷款年化利率;
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

    public Integer getPayWay_还款方式() {
        return payWay_还款方式;
    }

    public void setPayWay_还款方式(Integer payWay_还款方式) {
        this.payWay_还款方式 = payWay_还款方式;
    }

    public String getPayWayDisplay() {
        return payWayDisplay;
    }

    public void setPayWayDisplay(String payWayDisplay) {
        this.payWayDisplay = payWayDisplay;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getQrcode() {
        return qrcode;
    }
}
