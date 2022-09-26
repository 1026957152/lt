package com.lt.dom.oct.Axh;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.credit.PullRequest;
import com.lt.dom.otcenum.enum_.EnumXhPushRequestStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


@NoArgsConstructor
@Data
@Entity
public class PullFromYxdRequest {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id__")

    private long id;


    @JsonProperty("id")
    private Integer idX;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("tenantId")
    private String tenantId;
    @JsonProperty("instId")
    private Integer instId;
    @JsonProperty("entId")
    private Integer entId;
    @JsonProperty("entName")
    private String entName;
    @JsonProperty("creditCode")
    private String creditCode;
    @JsonProperty("linkMan")
    private String linkMan;
    @JsonProperty("linkPhone")
    private String linkPhone;
    @JsonProperty("evaluateId")
    private Integer evaluateId;
    @JsonProperty("productId")
    private Integer productId;
    @JsonProperty("productName")
    private String productName;
    @JsonProperty("productLogo")
    private String productLogo;
    @JsonProperty("productType")
    private String productType;
    @JsonProperty("loanAmountLow")
    private Integer loanAmountLow;
    @JsonProperty("loanAmountHigh")
    private Integer loanAmountHigh;
    @JsonProperty("loanRateLow")
    private Double loanRateLow;
    @JsonProperty("loanRateHigh")
    private Double loanRateHigh;
    @JsonProperty("loanLimitShort")
    private Integer loanLimitShort;
    @JsonProperty("loanLimitLong")
    private Integer loanLimitLong;
    @JsonProperty("guarantyWay")
    private String guarantyWay;
    @JsonProperty("businessType")
    private String businessType;
    @JsonProperty("backMoneyType")
    private String backMoneyType;
    @JsonProperty("loanTimeLimit")
    private String loanTimeLimit;
    @JsonProperty("liaison")
    private String liaison;
    @JsonProperty("telephone")
    private String telephone;

    @Length(max  =2000)
    @JsonProperty("productDetails")
    private String productDetails;
    @JsonProperty("publishTime")
    private String publishTime;
    @JsonProperty("verifyTime")
    private String verifyTime;
    @JsonProperty("bringTogetherTime")
    private String bringTogetherTime;
    @JsonProperty("startTime")
    private String startTime;
    @JsonProperty("endTime")
    private String endTime;
    @JsonProperty("status")
    private String status;
    @JsonProperty("instRejectReason")
    private String instRejectReason;
    @JsonProperty("createBy")
    private String createBy;
    /** 响应时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("createTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonProperty("updateBy")
    private String updateBy;
    @JsonProperty("updateTime")
    /** 响应时间 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    @JsonProperty("disabled")
    private Integer disabled;
    @JsonProperty("subjectName")
    private String subjectName;
    @JsonProperty("subjectValue")
    private String subjectValue;
    @JsonProperty("financingPurpose")
    private String financingPurpose;
    @JsonProperty("repaymentSource")
    private String repaymentSource;
    @JsonProperty("instName")
    private String instName;
    @JsonProperty("creditRating")
    private String creditRating;
    private String status_text;
    private EnumXhPushRequestStatus status_xh;
    private LocalDateTime createDate;


    public void setId(long id) {
        this.id = id;
    }

    public Integer getIdX() {
        return idX;
    }

    public void setIdX(Integer idX) {
        this.idX = idX;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getInstId() {
        return instId;
    }

    public void setInstId(Integer instId) {
        this.instId = instId;
    }

    public Integer getEntId() {
        return entId;
    }

    public void setEntId(Integer entId) {
        this.entId = entId;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public Integer getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(Integer evaluateId) {
        this.evaluateId = evaluateId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLogo() {
        return productLogo;
    }

    public void setProductLogo(String productLogo) {
        this.productLogo = productLogo;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getLoanAmountLow() {
        return loanAmountLow;
    }

    public void setLoanAmountLow(Integer loanAmountLow) {
        this.loanAmountLow = loanAmountLow;
    }

    public Integer getLoanAmountHigh() {
        return loanAmountHigh;
    }

    public void setLoanAmountHigh(Integer loanAmountHigh) {
        this.loanAmountHigh = loanAmountHigh;
    }

    public Double getLoanRateLow() {
        return loanRateLow;
    }

    public void setLoanRateLow(Double loanRateLow) {
        this.loanRateLow = loanRateLow;
    }

    public Double getLoanRateHigh() {
        return loanRateHigh;
    }

    public void setLoanRateHigh(Double loanRateHigh) {
        this.loanRateHigh = loanRateHigh;
    }

    public Integer getLoanLimitShort() {
        return loanLimitShort;
    }

    public void setLoanLimitShort(Integer loanLimitShort) {
        this.loanLimitShort = loanLimitShort;
    }

    public Integer getLoanLimitLong() {
        return loanLimitLong;
    }

    public void setLoanLimitLong(Integer loanLimitLong) {
        this.loanLimitLong = loanLimitLong;
    }

    public String getGuarantyWay() {
        return guarantyWay;
    }

    public void setGuarantyWay(String guarantyWay) {
        this.guarantyWay = guarantyWay;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBackMoneyType() {
        return backMoneyType;
    }

    public void setBackMoneyType(String backMoneyType) {
        this.backMoneyType = backMoneyType;
    }

    public String getLoanTimeLimit() {
        return loanTimeLimit;
    }

    public void setLoanTimeLimit(String loanTimeLimit) {
        this.loanTimeLimit = loanTimeLimit;
    }

    public String getLiaison() {
        return liaison;
    }

    public void setLiaison(String liaison) {
        this.liaison = liaison;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(String verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getBringTogetherTime() {
        return bringTogetherTime;
    }

    public void setBringTogetherTime(String bringTogetherTime) {
        this.bringTogetherTime = bringTogetherTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstRejectReason() {
        return instRejectReason;
    }

    public void setInstRejectReason(String instRejectReason) {
        this.instRejectReason = instRejectReason;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }


    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectValue() {
        return subjectValue;
    }

    public void setSubjectValue(String subjectValue) {
        this.subjectValue = subjectValue;
    }

    public String getFinancingPurpose() {
        return financingPurpose;
    }

    public void setFinancingPurpose(String financingPurpose) {
        this.financingPurpose = financingPurpose;
    }

    public String getRepaymentSource() {
        return repaymentSource;
    }

    public void setRepaymentSource(String repaymentSource) {
        this.repaymentSource = repaymentSource;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(String creditRating) {
        this.creditRating = creditRating;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_xh(EnumXhPushRequestStatus status_xh) {
        this.status_xh = status_xh;
    }

    public EnumXhPushRequestStatus getStatus_xh() {
        return status_xh;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}
