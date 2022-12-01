package com.lt.dom.otcReq;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class CitywalkLatLngPojo {

    


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



    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
