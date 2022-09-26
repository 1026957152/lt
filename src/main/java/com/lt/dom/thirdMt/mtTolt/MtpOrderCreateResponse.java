package com.lt.dom.thirdMt.mtTolt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MtpOrderCreateResponse {


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
        @JsonProperty("partnerOrderId")
        private String partnerOrderId;
    }
}
