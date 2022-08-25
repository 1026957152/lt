package com.lt.dom.vo;


import javax.validation.constraints.NotEmpty;


public class TravelerVo {


    @NotEmpty
    private String name;///

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
