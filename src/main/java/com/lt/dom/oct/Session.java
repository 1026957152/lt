package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumSessionFor;

import javax.persistence.*;

@Entity
public class Session {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private EnumSessionFor to;//

    private long toWho;//

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EnumSessionFor getTo() {
        return to;
    }

    public void setTo(EnumSessionFor to) {
        this.to = to;
    }

    public long getToWho() {
        return toWho;
    }

    public void setToWho(long toWho) {
        this.toWho = toWho;
    }

    private String browser;//
    //        string
    //The browser used in this browser session (e.g., Chrome).

    private String device;//
   //         string
   // Information about the device used for the browser session (e.g., Samsung SM-G930T).

    private String platform;//
    //        string
   // The platform for the browser session (e.g., Macintosh).


   //         string
    //The version for the browser session (e.g., 61.0.3163.100).


    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }


}
