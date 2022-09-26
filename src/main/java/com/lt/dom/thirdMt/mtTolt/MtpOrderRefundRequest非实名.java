package com.lt.dom.thirdMt.mtTolt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class MtpOrderRefundRequest非实名 {


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
        @JsonProperty("refundQuantity")
        private Integer refundQuantity;
        @JsonProperty("unitPrice")
        private Double unitPrice;
        @JsonProperty("refundPrice")
        private Double refundPrice;
        @JsonProperty("refundFee")
        private Double refundFee;
        @JsonProperty("refundTime")
        private String refundTime;
    }
}
