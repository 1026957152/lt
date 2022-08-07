package com.lt.dom.OctResp;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.hateoas.RepresentationModel;


public class PhoneResp extends RepresentationModel<PhoneResp> {
    private String errcode;  //错误信息
    private String errmsg; //错误码

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    private PhoneInfo phone_info;

    public PhoneInfo getPhone_info() {
        return phone_info;
    }

    public void setPhone_info(PhoneInfo phone_info) {
        this.phone_info = phone_info;
    }

    public static class PhoneInfo  {
        private String purePhoneNumber; //没有区号的手机号
        private String countryCode; //区号

        private String phoneNumber; //用户绑定的手机号（国外手机号会有区号）

        public String getPurePhoneNumber() {
            return purePhoneNumber;
        }

        public void setPurePhoneNumber(String purePhoneNumber) {
            this.purePhoneNumber = purePhoneNumber;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
