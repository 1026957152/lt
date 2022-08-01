package com.lt.dom.device;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PwdVerify {


    @JsonProperty("operator")
    private String operator;
    @JsonProperty("info")
    private InfoDTO info;


    public static class InfoDTO {
        @JsonProperty("DeviceID")
        private Integer deviceID;
        @JsonProperty("CreateTime ")
        private String createTime;
        @JsonProperty("HasPic ")
        private Integer hasPic;
        @JsonProperty("SanpPic")
        private String sanpPic;
    }
}
