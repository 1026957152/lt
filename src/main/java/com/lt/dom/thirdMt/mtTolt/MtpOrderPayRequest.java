package com.lt.dom.thirdMt.mtTolt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MtpOrderPayRequest {


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
        @JsonProperty("channel")
        private String channel;
        @JsonProperty("channelDetail")
        private String channelDetail;
    }
}
