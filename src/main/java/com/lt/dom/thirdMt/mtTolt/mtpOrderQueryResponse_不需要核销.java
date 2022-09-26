package com.lt.dom.thirdMt.mtTolt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class mtpOrderQueryResponse_不需要核销 {


    @JsonProperty("code")
    private Integer code;
    @JsonProperty("describe")
    private String describe;
    @JsonProperty("partnerId")
    private Integer partnerId;
    @JsonProperty("body")
    private BodyDTO body;

    @NoArgsConstructor
    @Data
    public static class BodyDTO {
        @JsonProperty("orderId")
        private Integer orderId;
        @JsonProperty("partnerOrderId")
        private String partnerOrderId;
        @JsonProperty("orderStatus")
        private Integer orderStatus;
        @JsonProperty("orderQuantity")
        private Integer orderQuantity;
        @JsonProperty("usedQuantity")
        private Integer usedQuantity;
        @JsonProperty("refundedQuantity")
        private Integer refundedQuantity;
        @JsonProperty("voucherType")
        private Integer voucherType;
        @JsonProperty("orderAdditionalList")
        private List<OrderAdditionalListDTO> orderAdditionalList;

        @NoArgsConstructor
        @Data
        public static class OrderAdditionalListDTO {
            @JsonProperty("orderAdditionKey")
            private Integer orderAdditionKey;
            @JsonProperty("orderAdditionValue")
            private String orderAdditionValue;
        }
    }
}
