package com.lt.dom.device;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Subscribe {


    @JsonProperty("operator")
    private String operator;
    @JsonProperty("info")
    private InfoDTO info;

    public static class InfoDTO {
        @JsonProperty("DeviceID")
        private Integer deviceID;
        @JsonProperty("Num")
        private Integer num;
        @JsonProperty("Topics")
        private List<String> topics;
        @JsonProperty("SubscribeAddr")
        private String subscribeAddr;
        @JsonProperty("SubscribeUrl")
        private SubscribeUrlDTO subscribeUrl;
        @JsonProperty("BeatInterval")
        private Integer beatInterval;
        @JsonProperty("ResumefromBreakpoint")
        private Integer resumefromBreakpoint;
        @JsonProperty("Auth")
        private String auth;


        public static class SubscribeUrlDTO {
            @JsonProperty("Snap")
            private String snap;
            @JsonProperty("Verify")
            private String verify;
            @JsonProperty("HeartBeat")
            private String heartBeat;
        }
    }
}
