package com.lt.dom.device;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SnapPush {


    @JsonProperty("operator")
    private String operator;
    @JsonProperty("info")
    private InfoDTO info;
    @JsonProperty("SanpPic")
    private String sanpPic;


    public static class InfoDTO {
        @JsonProperty("DeviceID")
        private Integer deviceID;
        @JsonProperty("CreateTime")
        private String createTime;
        @JsonProperty("PictureType")
        private Integer pictureType;
        @JsonProperty("Sendintime")
        private Integer sendintime;
    }
}
