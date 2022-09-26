package com.lt.dom.thirdMt.mtTolt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class OrderRescheduleRequest改签游玩日 {


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
        private Object rescheduleVisitors;
        @JsonProperty("originDate")
        private String originDate;
        @JsonProperty("targetDate")
        private String targetDate;
        @JsonProperty("originLevelIds")
        private List<Integer> originLevelIds;
        @JsonProperty("targetLevelIds")
        private List<Integer> targetLevelIds;
    }
}
