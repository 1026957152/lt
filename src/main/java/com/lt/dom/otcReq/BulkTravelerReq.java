package com.lt.dom.otcReq;


import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

public class BulkTravelerReq {


    @Valid
    @Size(min=1, max=100)
    List<TravelerReq> travelers;

    public List<TravelerReq> getTravelers() {
        return travelers;
    }

    public void setTravelers(List<TravelerReq> travelers) {
        this.travelers = travelers;
    }
}
