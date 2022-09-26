package com.lt.dom.thirdMt.mtTolt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class MtpOrderCreateRequest {


    @JsonProperty("partnerId")
    private Integer partnerId;
    @JsonProperty("body")
    private BodyDTO body;

    @NoArgsConstructor
    @Data
    public static class BodyDTO {
        @JsonProperty("orderId")
        private Integer orderId;
        @JsonProperty("partnerDealId")
        private String partnerDealId;
        @JsonProperty("mtDealId")
        private Integer mtDealId;
        @JsonProperty("buyPrice")
        private String buyPrice;
        @JsonProperty("unitPrice")
        private String unitPrice;
        @JsonProperty("totalPrice")
        private String totalPrice;
        @JsonProperty("quantity")
        private Integer quantity;
        @JsonProperty("visitors")
        private List<VisitorsDTO> visitors;
        @JsonProperty("travelDate")
        private String travelDate;

        @NoArgsConstructor
        @Data
        public static class VisitorsDTO {
            @JsonProperty("name")
            private String name;
            @JsonProperty("pinyin")
            private String pinyin;
            @JsonProperty("mobile")
            private String mobile;
            @JsonProperty("address")
            private String address;
            @JsonProperty("postCode")
            private String postCode;
            @JsonProperty("email")
            private String email;
            @JsonProperty("credentials")
            private CredentialsDTO credentials;
            @JsonProperty("gender")
            private Integer gender;
            @JsonProperty("additionalCredentials")
            private List<AdditionalCredentialsDTO> additionalCredentials;

            @NoArgsConstructor
            @Data
            public static class CredentialsDTO {
                @JsonProperty("0")
                private String $0;
            }

            @NoArgsConstructor
            @Data
            public static class AdditionalCredentialsDTO {
                @JsonProperty("credentialType")
                private String credentialType;
                @JsonProperty("credentialInfoMap")
                private CredentialInfoMapDTO credentialInfoMap;

                @NoArgsConstructor
                @Data
                public static class CredentialInfoMapDTO {
                    @JsonProperty("schoolName")
                    private String schoolName;
                    @JsonProperty("credentialNo")
                    private String credentialNo;
                }
            }
        }
    }
}
