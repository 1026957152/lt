package com.lt.dom.thirdTS.domainTsToLt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class LtRespToTs下单接口 {


    @JsonProperty("info")
    private InfoDTO info;
    @JsonProperty("message")
    private String message;
    @JsonProperty("success")
    private Boolean success;

    @NoArgsConstructor
    @Data
    public static class InfoDTO {
        @JsonProperty("id")
        private String id;
        @JsonProperty("is_send")
        private String isSend;
        @JsonProperty("code")
        private String code;
        @JsonProperty("codes")
        private List<String> codes;
        @JsonProperty("content")
        private String content;
        @JsonProperty("params")
        private ParamsDTO params;
        @JsonProperty("price")
        private String price;
        @JsonProperty("qrcode")
        private String qrcode;
        @JsonProperty("send_price")
        private String sendPrice;

        @NoArgsConstructor
        @Data
        public static class ParamsDTO {
            @JsonProperty("is_real_code")
            private Integer isRealCode;
            @JsonProperty("qrcode_images")
            private List<String>  qrcodeImages;

            public Integer getIsRealCode() {
                return isRealCode;
            }

            public void setIsRealCode(Integer isRealCode) {
                this.isRealCode = isRealCode;
            }

            public List<String> getQrcodeImages() {
                return qrcodeImages;
            }

            public void setQrcodeImages(List<String> qrcodeImages) {
                this.qrcodeImages = qrcodeImages;
            }
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsSend() {
            return isSend;
        }

        public void setIsSend(String isSend) {
            this.isSend = isSend;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public List<String> getCodes() {
            return codes;
        }

        public void setCodes(List<String> codes) {
            this.codes = codes;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public ParamsDTO getParams() {
            return params;
        }

        public void setParams(ParamsDTO params) {
            this.params = params;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getQrcode() {
            return qrcode;
        }

        public void setQrcode(String qrcode) {
            this.qrcode = qrcode;
        }

        public String getSendPrice() {
            return sendPrice;
        }

        public void setSendPrice(String sendPrice) {
            this.sendPrice = sendPrice;
        }
    }

    public InfoDTO getInfo() {
        return info;
    }

    public void setInfo(InfoDTO info) {
        this.info = info;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
