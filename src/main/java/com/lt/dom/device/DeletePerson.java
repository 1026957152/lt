package com.lt.dom.device;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class DeletePerson {


    @JsonProperty("operator")
    private String operator;
    @JsonProperty("info")
    private InfoDTO info;


    public static class InfoDTO {
        @JsonProperty("DeviceID")
        private Integer deviceID;
        @JsonProperty("TotalNum")
        private String totalNum;
        @JsonProperty("IdType")
        private Integer idType;
        @JsonProperty("PersonUUID")
        private List<String> personUUID;
    }
}
