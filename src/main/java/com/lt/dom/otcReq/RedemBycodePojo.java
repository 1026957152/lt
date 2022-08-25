package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumValidatorType;

public class RedemBycodePojo {

    private String code;
    private float latitude;
    private float longitude;

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }




    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


public static class Code {

    private String code;///
    private float latitude;///
    private float longitude;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}





}
