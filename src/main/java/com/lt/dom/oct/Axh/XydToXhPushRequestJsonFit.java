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
    @Data
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
