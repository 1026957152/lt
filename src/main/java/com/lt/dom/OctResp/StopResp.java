package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.Stop;
import com.lt.dom.otcenum.EnumStopArrivalStatus;
import com.lt.dom.otcenum.EnumStopStatus;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StopResp extends BaseResp {




    private String name;


    private String code;
    private Float lat;
    private Float lng;
    private long id;

    public static StopResp of(Stop e) {
        StopResp stopResp = new StopResp();
        stopResp.setCode(e.getCode());
        stopResp.setName(e.getName());
        stopResp.setCreatedDate(e.getCreatedDate());
        stopResp.setModifiedDate(e.getModifiedDate());

        return stopResp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLat() {
        return lat;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public Float getLng() {
        return lng;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
