package com.lt.dom.thiirdAli.idfaceIdentity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class IdfaceIdentityVo {


    @JsonProperty("error_code")
    private Integer errorCode;
    @JsonProperty("reason")
    private String reason;
    @JsonProperty("result")
    private ResultDTO result;

    @NoArgsConstructor
    @Data
    public static class ResultDTO {
        @JsonProperty("IdCardNo")
        private String idCardNo;
        @JsonProperty("Name")
        private String name;
        @JsonProperty("Validate_Result")
        private Integer validateResult;
        @JsonProperty("Similarity")
        private Integer similarity;

        public String getIdCardNo() {
            return idCardNo;
        }

        public void setIdCardNo(String idCardNo) {
            this.idCardNo = idCardNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValidateResult() {
            return validateResult;
        }

        public void setValidateResult(Integer validateResult) {
            this.validateResult = validateResult;
        }

        public Integer getSimilarity() {
            return similarity;
        }

        public void setSimilarity(Integer similarity) {
            this.similarity = similarity;
        }
    }

    public class IdfaceIdentityVoSimple {
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

    public  IdfaceIdentityVoSimple toSimple() {
        IdfaceIdentityVoSimple businessLicenseVoSimple = new IdfaceIdentityVoSimple();

        return businessLicenseVoSimple;

    }
}
