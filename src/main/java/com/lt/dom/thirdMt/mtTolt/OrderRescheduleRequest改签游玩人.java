package com.lt.dom.thirdMt.mtTolt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class OrderRescheduleRequest改签游玩人 {


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
        @JsonProperty("rescheduleType")
        private Integer rescheduleType;
        @JsonProperty("rescheduleNo")
        private String rescheduleNo;
        @JsonProperty("rescheduleVisitors")
        private List<RescheduleVisitorsDTO> rescheduleVisitors;
        @JsonProperty("originDate")
        private Object originDate;
        @JsonProperty("targetDate")
        private Object targetDate;
        @JsonProperty("originLevelIds")
        private List<Integer> originLevelIds;
        @JsonProperty("targetLevelIds")
        private List<Integer> targetLevelIds;

        @NoArgsConstructor
        @Data
        public static class RescheduleVisitorsDTO {
            @JsonProperty("originVisitor")
            private OriginVisitorDTO originVisitor;
            @JsonProperty("targetVisitor")
            private TargetVisitorDTO targetVisitor;

            @NoArgsConstructor
            @Data
            public static class OriginVisitorDTO {
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

                @NoArgsConstructor
                @Data
                public static class CredentialsDTO {
                    @JsonProperty("0")
                    private String $0;
                }
            }

            @NoArgsConstructor
            @Data
            public static class TargetVisitorDTO {
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

                @NoArgsConstructor
                @Data
                public static class CredentialsDTO {
                    @JsonProperty("0")
                    private String $0;
                }
            }
        }
    }
}
