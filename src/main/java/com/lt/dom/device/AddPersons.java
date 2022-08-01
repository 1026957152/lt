package com.lt.dom.device;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AddPersons {


    @JsonProperty("operator")
    private String operator;
    @JsonProperty("DeviceID")
    private Integer deviceID;
    @JsonProperty("Total")
    private Integer total;
    @JsonProperty("Personinfo_0")
    private Personinfo0DTO personinfo0;
    @JsonProperty("Personinfo_1")
    private Personinfo1DTO personinfo1;

    public static class Personinfo0DTO {
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
        @JsonProperty("WiegandType")
        private Integer wiegandType;
        @JsonProperty("CardMode")
        private Integer cardMode;
        @JsonProperty("MjCardNo")
        private Integer mjCardNo;
        @JsonProperty("Tempvalid")
        private Integer tempvalid;
        @JsonProperty("CustomizeID")
        private Integer customizeID;
        @JsonProperty("PersonUUID")
        private String personUUID;
        @JsonProperty("isCheckSimilarity")
        private Integer isCheckSimilarity;
        @JsonProperty("ValidBegin")
        private String validBegin;
        @JsonProperty("ValidEnd")
        private String validEnd;
        @JsonProperty("picinfo")
        private String picinfo;
    }


    public static class Personinfo1DTO {
    }
}
