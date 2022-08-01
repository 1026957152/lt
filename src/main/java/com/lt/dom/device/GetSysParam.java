package com.lt.dom.device;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetSysParam {

    @JsonProperty("operator")
    private String operator;
    @JsonProperty("info")
    private InfoDTO info;


    public static class InfoDTO {
        @JsonProperty("Name")
        private String name;
        @JsonProperty("DeviceID")
        private Integer deviceID;
        @JsonProperty("Version")
        private String version;
    }
}
