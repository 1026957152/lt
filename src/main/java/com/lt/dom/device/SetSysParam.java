package com.lt.dom.device;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SetSysParam {


    @JsonProperty("operator")
    private String operator;
    @JsonProperty("info")
    private InfoDTO info;


    public static class InfoDTO {
        @JsonProperty("Name")
        private String name;
    }
}
