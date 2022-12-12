package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Blog;
import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumAgentConnetionStatus;

import java.util.List;


//https://metmuseum.github.io/


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelResp extends BaseResp  {



    private String name;
    private String home_url;
    private String desc_short;
    private String desc_long;
    private EnumAgentConnetionStatus status;

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

    public void setStatus(EnumAgentConnetionStatus status) {
        this.status = status;
    }

    public EnumAgentConnetionStatus getStatus() {
        return status;
    }
}
