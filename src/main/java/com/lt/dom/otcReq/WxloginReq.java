package com.lt.dom.otcReq;


import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class WxloginReq {  // 这个就是机器了啊

    @NotEmpty
    private String code;

    @Valid
    private RawData rawData;

/*    @NotEmpty
    private String signature;*/


    public static class RawData {  // 这个就是机器了啊

        @NotEmpty
        private String nickName;
        @NotEmpty
        private String avatarUrl;
        @NotNull
        private Integer gender;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RawData getRawData() {
        return rawData;
    }

    public void setRawData(RawData rawData) {
        this.rawData = rawData;
    }

/*    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }*/
}
