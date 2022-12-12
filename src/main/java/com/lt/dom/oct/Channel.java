package com.lt.dom.oct;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.BaseResp;

import javax.persistence.Entity;


//https://metmuseum.github.io/

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Channel extends Base {



    private String name;
    private String home_url;
    private String desc_short;
    private String desc_long;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHome_url() {
        return home_url;
    }

    public void setHome_url(String home_url) {
        this.home_url = home_url;
    }

    public String getDesc_short() {
        return desc_short;
    }

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }

    public String getDesc_long() {
        return desc_long;
    }

    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }
}
