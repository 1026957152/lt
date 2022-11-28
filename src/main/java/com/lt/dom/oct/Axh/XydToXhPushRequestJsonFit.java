package com.lt.dom.oct.Axh;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public class XydToXhPushRequestJsonFit {


    @JsonProperty("baseInfo")
    private BaseInfoDTO baseInfo;
    @JsonProperty("orderId")
    private Integer orderId;
    @JsonProperty("taxInf")
    private List<TaxInfDTO> taxInf;
    @JsonProperty("productInfo")
    private ProductInfoDTO productInfo;
    @JsonProperty("instInfo")
    private InstInfoDTO instInfo;
    @JsonProperty("crAmount")
    private Float crAmount;

    public Float getCrAmount() {
        return crAmount;
    }

    public void setCrAmount(Float crAmount) {
        this.crAmount = crAmount;
    }

    public BaseInfoDTO getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfoDTO baseInfo) {
        this.baseInfo = baseInfo;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public List<TaxInfDTO> getTaxInf() {
        return taxInf;
    }

    public void setTaxInf(List<TaxInfDTO> taxInf) {
        this.taxInf = taxInf;
    }

    public ProductInfoDTO getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfoDTO productInfo) {
        this.productInfo = productInfo;
    }

    public InstInfoDTO getInstInfo() {
        return instInfo;
    }

    public void setInstInfo(InstInfoDTO instInfo) {
        this.instInfo = instInfo;
    }

    @NoArgsConstructor
    @Data
    public static class BaseInfoDTO {
        @JsonProperty("accountId")
        private Long accountId;
        @JsonProperty("address")
        private String address;
        @JsonProperty("authorizeTime")
        private Long authorizeTime;
        @JsonProperty("busLicense")
        private String busLicense;
        @JsonProperty("certificationTime")
        private Long certificationTime;
        @JsonProperty("createTime")
        private Long createTime;
        @JsonProperty("creditCode")
        private String creditCode;
        @JsonProperty("creditRating")
        private String creditRating;
        @JsonProperty("currencyCode")
        private String currencyCode;
        @JsonProperty("disabled")
        private Integer disabled;
        @JsonProperty("enabled")
        private Boolean enabled;
        @JsonProperty("entGdsszb")
        private Integer entGdsszb;
        @JsonProperty("entName")
        private String entName;
        @JsonProperty("entType")
        private String entType;
        @JsonProperty("entYgsl")
        private Integer entYgsl;
        @JsonProperty("entYysr")
        private Integer entYysr;
        @JsonProperty("entZcze")
        private Integer entZcze;
        @JsonProperty("financeAttachment")
        private String financeAttachment;
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("identification")
        private String identification;
        @JsonProperty("isAuthorize")
        private String isAuthorize;
        @JsonProperty("isBondIssue")
        private String isBondIssue;
        @JsonProperty("isCertified")
        private String isCertified;
        @JsonProperty("isDomesticListing")
        private String isDomesticListing;
        @JsonProperty("isHongkongListing")
        private String isHongkongListing;
        @JsonProperty("isListedCompany")
        private String isListedCompany;
        @JsonProperty("legalRepresentative")
        private String legalRepresentative;
        @JsonProperty("linkEmail")
        private String linkEmail;
        @JsonProperty("linkMan")
        private String linkMan;
        @JsonProperty("linkPhone")
        private String linkPhone;
        @JsonProperty("logo")
        private String logo;
        @JsonProperty("mainIndustryCode")
        private String mainIndustryCode;
        @JsonProperty("operatingStatus")
        private String operatingStatus;
        @JsonProperty("password")
        private String password;
        @JsonProperty("phone")
        private String phone;
        @JsonProperty("registeredCapital")
        private Double registeredCapital;
        @JsonProperty("setupDate")
        private Long setupDate;
        @JsonProperty("shareholderLayers")
        private Integer shareholderLayers;
        @JsonProperty("updateBy")
        private String updateBy;
        @JsonProperty("updateTime")
        private Long updateTime;
        @JsonProperty("userId")
        private Long userId;
        private FinanceAttachment financeAttachmentDto;

        public String getFinanceAttachment() {
            return financeAttachment;
        }

        public void setFinanceAttachment(String financeAttachment) {
            this.financeAttachment = financeAttachment;
        }

        public void setFinanceAttachmentDto(FinanceAttachment financeAttachmentDto) {
            this.financeAttachmentDto = financeAttachmentDto;
        }

        public FinanceAttachment getFinanceAttachmentDto() {
            return financeAttachmentDto;
        }
    }
    @NoArgsConstructor
    @Data
    public static class FinanceAttachment {

        @JsonProperty("status")
        private String status;
        @JsonProperty("name")
        private String name;
        @JsonProperty("size")
        private Integer size;
        @JsonProperty("percentage")
        private Integer percentage;
        @JsonProperty("uid")
        private Long uid;
        @JsonProperty("raw")
        private RawDTO raw;
        @JsonProperty("response")
        private ResponseDTO response;

        @NoArgsConstructor
        @Data
        public static class RawDTO {
            @JsonProperty("uid")
            private Long uid;
        }

        @NoArgsConstructor
        @Data
        public static class ResponseDTO {
            @JsonProperty("datas")
            private String datas;
            @JsonProperty("resp_code")
            private Integer respCode;
            @JsonProperty("resp_msg")
            private String respMsg;
        }
    }
    @NoArgsConstructor

    public static class ProductInfoDTO {
        @JsonProperty("applyCount")
        private String applyCount;
        @JsonProperty("createBy")
        private String createBy;
        @JsonProperty("createTime")
        private Long createTime;
        @JsonProperty("disabled")
        private Integer disabled;
        @JsonProperty("guarantyWay")
        private String guarantyWay;
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("institutionId")
        private Integer institutionId;
        @JsonProperty("liaison")
        private String liaison;
        @JsonProperty("loanAmountHigh")
        private String loanAmountHigh;
        @JsonProperty("loanAmountLow")
        private String loanAmountLow;
        @JsonProperty("loanLimitLong")
        private String loanLimitLong;
        @JsonProperty("loanLimitShort")
        private String loanLimitShort;
        @JsonProperty("loanRateHigh")
        private Double loanRateHigh;
        @JsonProperty("loanRateLow")
        private Double loanRateLow;
        @JsonProperty("productDetails")
        private String productDetails;
        @JsonProperty("productLogo")
        private String productLogo;
        @JsonProperty("productName")
        private String productName;
        @JsonProperty("productType")
        private String productType;
        @JsonProperty("publishTime")
        private String publishTime;
        @JsonProperty("status")
        private String status;
        @JsonProperty("telephone")
        private String telephone;
        @JsonProperty("tenantId")
        private Integer tenantId;
        @JsonProperty("updateBy")
        private String updateBy;
        @JsonProperty("updateTime")
        private Long updateTime;
        @JsonProperty("userId")
        private Long userId;
        @JsonProperty("verifyTime")
        private String verifyTime;

        public String getApplyCount() {
            return applyCount;
        }

        public void setApplyCount(String applyCount) {
            this.applyCount = applyCount;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public Integer getDisabled() {
            return disabled;
        }

        public void setDisabled(Integer disabled) {
            this.disabled = disabled;
        }

        public String getGuarantyWay() {
            return guarantyWay;
        }

        public void setGuarantyWay(String guarantyWay) {
            this.guarantyWay = guarantyWay;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getInstitutionId() {
            return institutionId;
        }

        public void setInstitutionId(Integer institutionId) {
            this.institutionId = institutionId;
        }

        public String getLiaison() {
            return liaison;
        }

        public void setLiaison(String liaison) {
            this.liaison = liaison;
        }

        public String getLoanAmountHigh() {
            return loanAmountHigh;
        }

        public void setLoanAmountHigh(String loanAmountHigh) {
            this.loanAmountHigh = loanAmountHigh;
        }

        public String getLoanAmountLow() {
            return loanAmountLow;
        }

        public void setLoanAmountLow(String loanAmountLow) {
            this.loanAmountLow = loanAmountLow;
        }

        public String getLoanLimitLong() {
            return loanLimitLong;
        }

        public void setLoanLimitLong(String loanLimitLong) {
            this.loanLimitLong = loanLimitLong;
        }

        public String getLoanLimitShort() {
            return loanLimitShort;
        }

        public void setLoanLimitShort(String loanLimitShort) {
            this.loanLimitShort = loanLimitShort;
        }

        public Double getLoanRateHigh() {
            return loanRateHigh;
        }

        public void setLoanRateHigh(Double loanRateHigh) {
            this.loanRateHigh = loanRateHigh;
        }

        public Double getLoanRateLow() {
            return loanRateLow;
        }

        public void setLoanRateLow(Double loanRateLow) {
            this.loanRateLow = loanRateLow;
        }

        public String getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(String productDetails) {
            this.productDetails = productDetails;
        }

        public String getProductLogo() {
            return productLogo;
        }

        public void setProductLogo(String productLogo) {
            this.productLogo = productLogo;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
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

        public Long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Long updateTime) {
            this.updateTime = updateTime;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getVerifyTime() {
            return verifyTime;
        }

        public void setVerifyTime(String verifyTime) {
            this.verifyTime = verifyTime;
        }
    }

    @NoArgsConstructor
    @Data
    public static class InstInfoDTO {
        @JsonProperty("account")
        private String account;
        @JsonProperty("accountId")
        private Long accountId;
        @JsonProperty("createBy")
        private String createBy;
        @JsonProperty("createTime")
        private Long createTime;
        @JsonProperty("disabled")
        private String disabled;
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("instAddress")
        private String instAddress;
        @JsonProperty("instCreditCode")
        private String instCreditCode;
        @JsonProperty("instEmail")
        private String instEmail;
        @JsonProperty("instItemType")
        private String instItemType;
        @JsonProperty("instLandline")
        private String instLandline;
        @JsonProperty("instLicense")
        private String instLicense;
        @JsonProperty("instLinkPerson")
        private String instLinkPerson;
        @JsonProperty("instLinkPhone")
        private String instLinkPhone;
        @JsonProperty("instLogo")
        private String instLogo;
        @JsonProperty("instName")
        private String instName;
        @JsonProperty("instType")
        private String instType;
        @JsonProperty("tenantId")
        private Integer tenantId;
        @JsonProperty("updateBy")
        private String updateBy;
        @JsonProperty("updateTime")
        private Long updateTime;
        @JsonProperty("userId")
        private Integer userId;
    }

    @NoArgsConstructor
    @Data
    public static class TaxInfDTO {
        @JsonProperty("data")
        private List<DataDTO> data;
        @JsonProperty("metadata")
        private MetadataDTO metadata;

        @NoArgsConstructor
        @Data
        public static class MetadataDTO {
            @JsonProperty("NSRMC")
            private String nsrmc;
            @JsonProperty("SHXYDM")
            private String shxydm;
            @JsonProperty("PJND")
            private String pjnd;
            @JsonProperty("PDJB")
            private String pdjb;
        }

        @NoArgsConstructor
        @Data
        public static class DataDTO {
            @JsonProperty("NSRMC")
            private String nsrmc;
            @JsonProperty("PJND")
            private String pjnd;
            @JsonProperty("PDJB")
            private String pdjb;
            @JsonProperty("SHXYDM")
            private String shxydm;
        }
    }
}
