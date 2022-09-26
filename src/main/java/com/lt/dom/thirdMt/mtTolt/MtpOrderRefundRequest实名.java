package com.lt.dom.thirdMt.mtTolt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class MtpOrderRefundRequest实名 {

    @JsonProperty("partnerId")
    private Integer partnerId;
    @JsonProperty("body")
    private BodyDTO body;

    @NoArgsConstructor
    @Data
    public static class BodyDTO {
        @JsonProperty("orderId")
        private Integer orderId;
        @JsonProperty("refundId")
        private String refundId;
        @JsonProperty("partnerOrderId")
        private String partnerOrderId;
        @JsonProperty("partnerDealId")
        private String partnerDealId;
        @JsonProperty("voucherList")
        private List<String> voucherList;
        @JsonProperty("credentialList")
        private List<CredentialListDTO> credentialList;
        @JsonProperty("refundQuantity")
        private Integer refundQuantity;
        @JsonProperty("unitPrice")
        private String unitPrice;
        @JsonProperty("refundPrice")
        private String refundPrice;
        @JsonProperty("refundFee")
        private String refundFee;
        @JsonProperty("refundTime")
        private String refundTime;

        @NoArgsConstructor
        @Data
        public static class CredentialListDTO {
            @JsonProperty("credentialType")
            private Integer credentialType;
            @JsonProperty("credentialNo")
            private String credentialNo;
            @JsonProperty("voucher")
            private String voucher;
        }
    }
}
