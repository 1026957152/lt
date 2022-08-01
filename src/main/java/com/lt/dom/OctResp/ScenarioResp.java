package com.lt.dom.OctResp;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class ScenarioResp {


    private String name;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
