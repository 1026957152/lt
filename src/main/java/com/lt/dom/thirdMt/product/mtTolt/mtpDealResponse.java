package com.lt.dom.thirdMt.product.mtTolt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class mtpDealResponse {


    @JsonProperty("code")
    private Integer code;
    @JsonProperty("describe")
    private String describe;
    @JsonProperty("partnerId")
    private Integer partnerId;
    @JsonProperty("totalSize")
    private Integer totalSize;
    @JsonProperty("body")
    private List<BodyDTO> body;

    @NoArgsConstructor
    @Data
    public static class BodyDTO {
        @JsonProperty("useDateMode")
        private Integer useDateMode;
        @JsonProperty("visitorInfoRule")
        private VisitorInfoRuleDTO visitorInfoRule;
        @JsonProperty("visitorInfoType")
        private String visitorInfoType;
        @JsonProperty("visitorInfoGroupSize")
        private String visitorInfoGroupSize;
        @JsonProperty("refundType")
        private String refundType;
        @JsonProperty("partlyRefund")
        private Boolean partlyRefund;
        @JsonProperty("refundNote")
        private String refundNote;
        @JsonProperty("refundStairsRules")
        private List<RefundStairsRulesDTO> refundStairsRules;
        @JsonProperty("scheduleOnlineTime")
        private String scheduleOnlineTime;
        @JsonProperty("scheduleOfflineTime")
        private String scheduleOfflineTime;
        @JsonProperty("servicePhones")
        private List<ServicePhonesDTO> servicePhones;
        @JsonProperty("getInAddresses")
        private List<String> getInAddresses;
        @JsonProperty("needTicket")
        private Boolean needTicket;
        @JsonProperty("getTicketRule")
        private GetTicketRuleDTO getTicketRule;
        @JsonProperty("ticketGetAddress")
        private List<String> ticketGetAddress;
        @JsonProperty("orderCancelTime")
        private Integer orderCancelTime;
        @JsonProperty("otherNote")
        private String otherNote;
        @JsonProperty("returnVoucher")
        private Boolean returnVoucher;
        @JsonProperty("voucherText")
        private String voucherText;
        @JsonProperty("getVoucherRule")
        private GetVoucherRuleDTO getVoucherRule;
        @JsonProperty("stock")
        private Integer stock;
        @JsonProperty("marketPrice")
        private Integer marketPrice;
        @JsonProperty("mtPrice")
        private Integer mtPrice;
        @JsonProperty("settlementPrice")
        private Double settlementPrice;
        @JsonProperty("voucherTimeBegin")
        private Integer voucherTimeBegin;
        @JsonProperty("voucherTimeEnd")
        private Integer voucherTimeEnd;
        @JsonProperty("aheadHoursType")
        private Integer aheadHoursType;
        @JsonProperty("aheadMinutes")
        private Integer aheadMinutes;
        @JsonProperty("aheadTimeRules")
        private List<AheadTimeRulesDTO> aheadTimeRules;
        @JsonProperty("idLimitDays")
        private Integer idLimitDays;
        @JsonProperty("idLimitCount")
        private Integer idLimitCount;
        @JsonProperty("phoneLimitDays")
        private Integer phoneLimitDays;
        @JsonProperty("phoneLimitCount")
        private Integer phoneLimitCount;
        @JsonProperty("maxBuyCount")
        private Integer maxBuyCount;
        @JsonProperty("minBuyCount")
        private Integer minBuyCount;
        @JsonProperty("title")
        private String title;
        @JsonProperty("subTitle")
        private String subTitle;
        @JsonProperty("include")
        private String include;
        @JsonProperty("exclude")
        private String exclude;
        @JsonProperty("partnerId")
        private Integer partnerId;
        @JsonProperty("partnerDealId")
        private String partnerDealId;
        @JsonProperty("partnerPoiId")
        private String partnerPoiId;
        @JsonProperty("dealImageInfos")
        private List<DealImageInfosDTO> dealImageInfos;
        @JsonProperty("canRefundCurrentDate")
        private Boolean canRefundCurrentDate;
        @JsonProperty("hasRefundLimitTime")
        private Boolean hasRefundLimitTime;
        @JsonProperty("refundLimitHourFrom")
        private Integer refundLimitHourFrom;
        @JsonProperty("refundLimitMinuteFrom")
        private Integer refundLimitMinuteFrom;
        @JsonProperty("refundLimitHourTo")
        private Integer refundLimitHourTo;
        @JsonProperty("refundLimitMinuteTo")
        private Integer refundLimitMinuteTo;
        @JsonProperty("discountCrowds")
        private List<DiscountCrowdsDTO> discountCrowds;

        @NoArgsConstructor
        @Data
        public static class VisitorInfoRuleDTO {
            @JsonProperty("name")
            private Boolean name;
            @JsonProperty("pinyin")
            private Boolean pinyin;
            @JsonProperty("mobile")
            private Boolean mobile;
            @JsonProperty("address")
            private Boolean address;
            @JsonProperty("postCode")
            private Boolean postCode;
            @JsonProperty("credentials")
            private Boolean credentials;
            @JsonProperty("email")
            private Boolean email;
            @JsonProperty("hkmlp")
            private Boolean hkmlp;
            @JsonProperty("tlp")
            private Boolean tlp;
            @JsonProperty("mtp")
            private Boolean mtp;
            @JsonProperty("passport")
            private Boolean passport;
        }

        @NoArgsConstructor
        @Data
        public static class GetTicketRuleDTO {
            @JsonProperty("effectiveCertificate")
            private Boolean effectiveCertificate;
            @JsonProperty("voucherLoaders")
            private List<Integer> voucherLoaders;
            @JsonProperty("needAll")
            private Boolean needAll;
            @JsonProperty("needCertificateSupplement")
            private Boolean needCertificateSupplement;
            @JsonProperty("certificateSupplement")
            private String certificateSupplement;
            @JsonProperty("needVoucherSupplement ")
            private Boolean needVoucherSupplement;
            @JsonProperty("voucherSupplement ")
            private String voucherSupplement;
        }

        @NoArgsConstructor
        @Data
        public static class GetVoucherRuleDTO {
            @JsonProperty("effectiveCertificate")
            private Boolean effectiveCertificate;
            @JsonProperty("voucherLoaders")
            private List<Integer> voucherLoaders;
            @JsonProperty("needAll")
            private Boolean needAll;
            @JsonProperty("needCertificateSupplement")
            private Boolean needCertificateSupplement;
            @JsonProperty("certificateSupplement")
            private String certificateSupplement;
            @JsonProperty("needVoucherSupplement ")
            private Boolean needVoucherSupplement;
            @JsonProperty("voucherSupplement ")
            private String voucherSupplement;
        }

        @NoArgsConstructor
        @Data
        public static class RefundStairsRulesDTO {
            @JsonProperty("refundTimeType")
            private String refundTimeType;
            @JsonProperty("refundLimitMinutes")
            private Integer refundLimitMinutes;
            @JsonProperty("refundFeeMode")
            private Integer refundFeeMode;
            @JsonProperty("refundFee")
            private Integer refundFee;
            @JsonProperty("refundNote")
            private String refundNote;
        }

        @NoArgsConstructor
        @Data
        public static class ServicePhonesDTO {
            @JsonProperty("phone")
            private String phone;
            @JsonProperty("startHour")
            private String startHour;
            @JsonProperty("startMin")
            private String startMin;
            @JsonProperty("endHour")
            private String endHour;
            @JsonProperty("endMin")
            private String endMin;
        }

        @NoArgsConstructor
        @Data
        public static class AheadTimeRulesDTO {
            @JsonProperty("aheadHoursType")
            private String aheadHoursType;
            @JsonProperty("aheadMinutes")
            private Integer aheadMinutes;
        }

        @NoArgsConstructor
        @Data
        public static class DealImageInfosDTO {
            @JsonProperty("imageName")
            private String imageName;
            @JsonProperty("imageUrl")
            private String imageUrl;
            @JsonProperty("frontImage")
            private Boolean frontImage;
        }

        @NoArgsConstructor
        @Data
        public static class DiscountCrowdsDTO {
            @JsonProperty("crowdLimits")
            private List<CrowdLimitsDTO> crowdLimits;
            @JsonProperty("multiLimit")
            private Integer multiLimit;
            @JsonProperty("crowdId")
            private Integer crowdId;
            @JsonProperty("canSchedule")
            private Boolean canSchedule;
            @JsonProperty("scheduleInfoList")
            private List<ScheduleInfoListDTO> scheduleInfoList;

            @NoArgsConstructor
            @Data
            public static class CrowdLimitsDTO {
                @JsonProperty("limitType")
                private Integer limitType;
                @JsonProperty("limitDesc")
                private String limitDesc;
            }

            @NoArgsConstructor
            @Data
            public static class ScheduleInfoListDTO {
                @JsonProperty("scheduleLimitMinutes")
                private Integer scheduleLimitMinutes;
                @JsonProperty("identicalDate")
                private Integer identicalDate;
                @JsonProperty("type")
                private Integer type;
                @JsonProperty("scheduleCount")
                private Integer scheduleCount;
            }
        }
    }
}
