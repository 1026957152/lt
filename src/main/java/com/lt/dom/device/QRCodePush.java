package com.lt.dom.device;

import com.fasterxml.jackson.annotation.JsonProperty;


public class QRCodePush {


    @JsonProperty("operator")
    private String operator;
    @JsonProperty("info")
    private InfoDTO info;


    public static class InfoDTO {
        @JsonProperty("DeviceID")
        private Integer deviceID;
        @JsonProperty("QRcodeInfo")
        private String qRcodeInfo;
    }
}
