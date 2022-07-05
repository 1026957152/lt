package com.lt.dom.oct.ValidatorParking;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VounchLicense {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @JsonProperty("id")
    private long id;
    private String license;

    private long vounchId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public long getVounchId() {
        return vounchId;
    }

    public void setVounchId(long vounchId) {
        this.vounchId = vounchId;
    }
}
