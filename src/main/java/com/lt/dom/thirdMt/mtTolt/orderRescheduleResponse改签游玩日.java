package com.lt.dom.thirdMt.mtTolt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class orderRescheduleResponse改签游玩日 {


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
        private Long orderId;
        @JsonProperty("rescheduleNo")
        private String rescheduleNo;
        @JsonProperty("successCount")
        private Integer successCount;
        @JsonProperty("credentialVouchers")
        private List<CredentialVouchersDTO> credentialVouchers;

        @NoArgsConstructor
        @Data
        public static class CredentialVouchersDTO {
            @JsonProperty("credentialType")
            private Integer credentialType;
            @JsonProperty("credentialNo")
            private String credentialNo;
            @JsonProperty("voucher")
            private String voucher;
            @JsonProperty("voucherPicture")
            private String voucherPicture;
            @JsonProperty("additionMap")
            private AdditionMapDTO additionMap;

            @NoArgsConstructor
            @Data
            public static class AdditionMapDTO {
                @JsonProperty("1")
                private String $1;
            }
        }
    }
}
