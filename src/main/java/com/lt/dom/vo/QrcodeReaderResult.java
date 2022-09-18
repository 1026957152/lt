package com.lt.dom.vo;

import com.lt.dom.oct.AssetDevice;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Peter Xu on 01/18/2015.
 */
public class QrcodeReaderResult implements Serializable{

    private String vgdecoderesult;

    private String devicenumber;



    public QrcodeReaderResult(String vgdecoderesult, String devicenumber) {


        this.devicenumber = devicenumber;
        this.vgdecoderesult = vgdecoderesult;
    }

    public String getVgdecoderesult() {
        return vgdecoderesult;
    }

    public void setVgdecoderesult(String vgdecoderesult) {
        this.vgdecoderesult = vgdecoderesult;
    }

    public String getDevicenumber() {
        return devicenumber;
    }

    public void setDevicenumber(String devicenumber) {
        this.devicenumber = devicenumber;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
