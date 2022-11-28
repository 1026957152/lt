package com.lt.dom.OctResp;

import com.lt.dom.oct.BusStop;

import java.time.LocalTime;
import java.util.List;

//https://www3.septa.org/#/Real%20Time%20Data/trainView

public class BusStopResp {
    private Double lat;
    private Double lng;
    private List places;
    private LocalTime departure_time;

    public static BusStopResp of(BusStop e) {
        BusStopResp busStopResp = new BusStopResp();
        busStopResp.setCode(e.getCode());
        busStopResp.setName(e.getName());
        busStopResp.setLat(e.getLat());
        busStopResp.setLng(e.getLng());
        busStopResp.setDescription(e.getDescription());
        busStopResp.setId(e.getId());
        return busStopResp;
    }


    public static BusStopResp fromWithId(BusStop busStop) {

        BusStopResp busStopReq = new BusStopResp();
        busStopReq.setLat(busStop.getLat());
        busStopReq.setId(busStop.getId());
        busStopReq.setLng(busStop.getLng());
        busStopReq.setName(busStop.getName());
        busStopReq.setDescription(busStop.getDescription());
        busStopReq.setCode(busStop.getCode());
        busStopReq.setShortName(busStop.getShortName());
        return busStopReq;
    }

    public static BusStopResp from(BusStop busStop) {

        BusStopResp busStopReq = new BusStopResp();
        busStopReq.setLat(busStop.getLat());
        busStopReq.setLng(busStop.getLng());
        busStopReq.setName(busStop.getName());
        busStopReq.setDescription(busStop.getDescription());
        busStopReq.setCode(busStop.getCode());
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

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLat() {
        return lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLng() {
        return lng;
    }

    public <R> void setPlaces(List places) {
        this.places = places;
    }

    public List getPlaces() {
        return places;
    }

    public void setDeparture_time(LocalTime departure_time) {
        this.departure_time = departure_time;
    }

    public LocalTime getDeparture_time() {
        return departure_time;
    }
}
