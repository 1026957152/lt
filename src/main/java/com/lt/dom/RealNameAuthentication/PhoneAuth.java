package com.lt.dom.RealNameAuthentication;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class PhoneAuth {
    private String idCardName;// String 否 身份证姓名
    private String idCardNo;// String 否 身份证号码
    private String phoneNo;// String 否 手机号码
    private String referTraveler;

    public String getIdCardName() {
        return idCardName;
    }

    public void setIdCardName(String idCardName) {
        this.idCardName = idCardName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setReferTraveler(String referTraveler) {
        this.referTraveler = referTraveler;
    }

    public String getReferTraveler() {
        return referTraveler;
    }
}
