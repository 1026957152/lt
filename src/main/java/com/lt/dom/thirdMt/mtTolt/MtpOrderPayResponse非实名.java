package com.lt.dom.thirdMt.mtTolt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class MtpOrderPayResponse非实名 {


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
        @JsonProperty("voucherType")
        private Integer voucherType;
        @JsonProperty("vouchers")
        private List<String> vouchers;
        @JsonProperty("voucherPics")
        private List<String> voucherPics;
        @JsonProperty("voucherAdditionalList")
        private List<VoucherAdditionalListDTO> voucherAdditionalList;
        @JsonProperty("orderAdditionalList")
        private List<OrderAdditionalListDTO> orderAdditionalList;

        @NoArgsConstructor
        @Data
        public static class VoucherAdditionalListDTO {
            @JsonProperty("voucher")
            private String voucher;
            @JsonProperty("additionMap")
            private AdditionMapDTO additionMap;

            @NoArgsConstructor
            @Data
            public static class AdditionMapDTO {
                @JsonProperty("1")
                private String $1;
            }
        }

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
