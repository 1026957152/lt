package com.lt.dom.device;

import com.fasterxml.jackson.annotation.JsonProperty;


public class VerifyPush {


    @JsonProperty("operator")
    private String operator;
    @JsonProperty("info")
    private InfoDTO info;
    @JsonProperty("RegisteredPic")
    private String registeredPic;


    public static class InfoDTO {
        @JsonProperty("DeviceID")
        private Integer deviceID;
        @JsonProperty("PersonID")
        private Integer personID;
        @JsonProperty("CreateTime")
        private String createTime;
        @JsonProperty("Similarity1")
        private Integer similarity1;
        @JsonProperty("Similarity2")
        private Integer similarity2;
        @JsonProperty("VerifyStatus")
        private Integer verifyStatus;
        @JsonProperty("VerfyType")
        private Integer verfyType;
        @JsonProperty("PersonType")
        private Integer personType;
        @JsonProperty("Name")
        private String name;
        @JsonProperty("Gender")
        private Integer gender;
        @JsonProperty("Nation")
        private Integer nation;
        @JsonProperty("CardType")
        private Integer cardType;
        @JsonProperty("IdCard")
        private String idCard;
        @JsonProperty("Birthday")
        private String birthday;
        @JsonProperty("Telnum")
        private String telnum;
        @JsonProperty("Native")
        private String nativeX;
        @JsonProperty("Address")
        private String address;
        @JsonProperty("Notes")
        private String notes;
        @JsonProperty("MjCardFrom")
        private Integer mjCardFrom;
        @JsonProperty("WGFacilityCode")
        private String wGFacilityCode;
        @JsonProperty("MjCardNo")
        private String mjCardNo;
        @JsonProperty("RFIDCard")
        private String rFIDCard;
        @JsonProperty("Tempvalid")
        private Integer tempvalid;
        @JsonProperty("CustomizeID")
        private Integer customizeID;
        @JsonProperty("PersonUUID")
        private String personUUID;
        @JsonProperty("ValidBegin")
        private String validBegin;
        @JsonProperty("ValidEnd")
        private String validEnd;
        @JsonProperty("Sendintime")
        private Integer sendintime;
    }
}
