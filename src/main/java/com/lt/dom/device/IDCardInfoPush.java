package com.lt.dom.device;

import com.fasterxml.jackson.annotation.JsonProperty;


public class IDCardInfoPush {


    @JsonProperty("operator")
    private String operator;
    @JsonProperty("info")
    private InfoDTO info;
    @JsonProperty("IDCard_photo")
    private String idcardPhoto;


    public static class InfoDTO {
        @JsonProperty("DeviceID")
        private Integer deviceID;
        @JsonProperty("IDCard_Idno")
        private String idcardIdno;
        @JsonProperty("IDCard_Name")
        private String idcardName;
        @JsonProperty("IDCard_Gender")
        private Integer idcardGender;
        @JsonProperty("IDCard_Nation")
        private Integer idcardNation;
        @JsonProperty("IDCard_Birthday")
        private String idcardBirthday;
        @JsonProperty("IDCard_Address")
        private String idcardAddress;
        @JsonProperty("IDCard_Idissue")
        private String idcardIdissue;
        @JsonProperty("IDCard_Idperiod")
        private String idcardIdperiod;
    }
}
