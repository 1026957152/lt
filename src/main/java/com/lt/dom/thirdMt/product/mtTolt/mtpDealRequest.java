package com.lt.dom.thirdMt.product.mtTolt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class mtpDealRequest {


    @JsonProperty("partnerId")
    private Integer partnerId;
    @JsonProperty("body")
    private BodyDTO body;

    @NoArgsConstructor
    @Data
    public static class BodyDTO {
        @JsonProperty("method")
        private String method;
        @JsonProperty("currentPage")
        private Object currentPage;
        @JsonProperty("pageSize")
        private Object pageSize;
        @JsonProperty("partnerDealIds")
        private List<String> partnerDealIds;
    }
}
