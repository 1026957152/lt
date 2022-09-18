package com.lt.dom.thiirdAli.ocr_business_license;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BusinessLicenseVo {


    @JsonProperty("config_str")
    private String configStr;
    @JsonProperty("angle")
    private Integer angle;
    @JsonProperty("reg_num")
    private String regNum;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("person")
    private String person;
    @JsonProperty("establish_date")
    private String establishDate;
    @JsonProperty("valid_period")
    private String validPeriod;
    @JsonProperty("address")
    private String address;
    @JsonProperty("capital")
    private String capital;
    @JsonProperty("business")
    private String business;
    @JsonProperty("emblem")
    private String emblem;
    @JsonProperty("title")
    private String title;
    @JsonProperty("stamp")
    private String stamp;
    @JsonProperty("qrcode")
    private String qrcode;
    @JsonProperty("is_fake")
    private Boolean isFake;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("request_id")
    private String requestId;

    public String getConfigStr() {
        return configStr;
    }

    public void setConfigStr(String configStr) {
        this.configStr = configStr;
    }

    public Integer getAngle() {
        return angle;
    }

    public void setAngle(Integer angle) {
        this.angle = angle;
    }

    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(String establishDate) {
        this.establishDate = establishDate;
    }

    public String getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(String validPeriod) {
        this.validPeriod = validPeriod;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getEmblem() {
        return emblem;
    }

    public void setEmblem(String emblem) {
        this.emblem = emblem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public Boolean getFake() {
        return isFake;
    }

    public void setFake(Boolean fake) {
        isFake = fake;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }


    public class BusinessLicenseVoSimple {
        @JsonProperty("reg_num")
        private String regNum;
        @JsonProperty("name")
        private String name;
        @JsonProperty("type")
        private String type;
        @JsonProperty("person")
        private String person;
        @JsonProperty("address")
        private String address;

        @JsonProperty("business")
        private String business;

        public String getRegNum() {
            return regNum;
        }

        public void setRegNum(String regNum) {
            this.regNum = regNum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPerson() {
            return person;
        }

        public void setPerson(String person) {
            this.person = person;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }
    }

    public  BusinessLicenseVoSimple toSimple() {
        BusinessLicenseVoSimple businessLicenseVoSimple = new BusinessLicenseVoSimple();
        businessLicenseVoSimple.setRegNum(this.regNum);
        businessLicenseVoSimple.setName(this.name);
        businessLicenseVoSimple.setType(this.type);
        businessLicenseVoSimple.setPerson(this.person);
        businessLicenseVoSimple.setAddress(this.address);
        businessLicenseVoSimple.setBusiness(this.business);
        return businessLicenseVoSimple;

    }
}
