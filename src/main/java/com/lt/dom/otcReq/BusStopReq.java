package com.lt.dom.otcReq;

import com.lt.dom.oct.BusStop;

import java.time.LocalDateTime;

//https://www3.septa.org/#/Real%20Time%20Data/trainView

public class BusStopReq {
    private Double lat;
    private Double lng;

    public static BusStopReq from(BusStop busStop) {

        BusStopReq busStopReq = new BusStopReq();
        busStopReq.setLat(busStop.getLat());
        busStopReq.setLng(busStop.getLng());
        busStopReq.setName(busStop.getName());
        busStopReq.setDescription(busStop.getDescription());
        busStopReq.setShortName(busStop.getShortName());
        return busStopReq;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

    private String code;

    private String shortName;
    private String name;
    private String description;


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
