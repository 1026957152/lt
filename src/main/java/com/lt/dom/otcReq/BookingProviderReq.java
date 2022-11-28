package com.lt.dom.otcReq;

import com.lt.dom.oct.Base;

import javax.persistence.Entity;


//https://developer.makeplans.com/#slots

@Entity
public class BookingProviderReq extends Base {


    private Long resource;
    private Long service;



    private Boolean active = false;

    public Long getResource() {
        return resource;
    }

    public void setResource(Long resource) {
        this.resource = resource;
    }

    public Long getService() {
        return service;
    }

    public void setService(Long service) {
        this.service = service;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
