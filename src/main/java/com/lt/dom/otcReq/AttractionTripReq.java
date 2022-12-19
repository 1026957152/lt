package com.lt.dom.otcReq;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.vo.ImageVo;

import javax.validation.constraints.NotNull;


public class AttractionTripReq {


    private Long id;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
