package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SmsResp {


    @JsonProperty("code")
    private Integer code;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("fee")
    private Double fee;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("sid")
    private Long sid;
}
