package com.lt.dom.vo;

import com.lt.dom.otcenum.EnumRequestApprove;
import com.lt.dom.otcenum.EnumRequestApproveReject;
import org.springframework.hateoas.Link;

public class FlowButtonVo {

    private EnumRequestApprove type;

    private Link link;
    private String id;

    public FlowButtonVo(EnumRequestApprove request, Link link, String id) {
        this.type = request;
        this.link = link;
        this.id = id;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
