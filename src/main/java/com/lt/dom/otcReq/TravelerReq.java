package com.lt.dom.otcReq;


import lombok.Builder;

import javax.validation.constraints.NotEmpty;


public class TravelerReq {


    @NotEmpty
    private String name;///

    private String family_name;///
    private String given_name;///
    @NotEmpty
    private String id;
    @NotEmpty
    private String tel_home;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTel_home() {
        return tel_home;
    }

    public void setTel_home(String tel_home) {
        this.tel_home = tel_home;
    }
}
