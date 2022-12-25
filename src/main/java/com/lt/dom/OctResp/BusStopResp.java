package com.lt.dom.OctResp;

import com.lt.dom.oct.StopRegistration;
import com.lt.dom.proto.BusStopRespProto;
import com.lt.dom.proto.req.BusAndStopUpdateMessage;
import com.lt.dom.oct.BusStop;
import com.lt.dom.serviceOtc.CryptoServiceImpl;

import java.time.LocalTime;
import java.util.List;

//https://www3.septa.org/#/Real%20Time%20Data/trainView

public class BusStopResp extends BaseResp {
    private Double lat;
    private Double lng;
    private List places;
    private LocalTime departure_time;
    private String number;

    public static BusStopResp of(BusStop e) {
        BusStopResp busStopResp = new BusStopResp();
        busStopResp.setCode(e.getCode());
        busStopResp.setName(e.getName());
        busStopResp.setLat(e.getLat());
        busStopResp.setLng(e.getLng());
        busStopResp.setDescription(e.getDescription());
        busStopResp.setId(e.getId());

        busStopResp.setCreatedDate(e.getCreatedDate());
        busStopResp.setModifiedDate(e.getModifiedDate());
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
    public static BusStopResp fromWithId(StopRegistration busStop) {

        BusStopResp busStopReq = new BusStopResp();
        busStopReq.setLat(busStop.getStop().getLat());
        busStopReq.setId(busStop.getId().hashCode()+0l);
        busStopReq.setLng(busStop.getStop().getLng());
        busStopReq.setName(busStop.getStop().getName());
        busStopReq.setDescription(busStop.getStop().getDescription());
      //  busStopReq.setCode(busStop.getId().hashCode()+"");
        busStopReq.setNumber(busStop.getId().getRouteId()+""+busStop.getId().getStopId());
        busStopReq.setShortName(busStop.getStop().getShortName());
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

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }


    public static class Live {

        private String departure_time;
        private String departure_distance;

        public String getDeparture_time() {
            return departure_time;
        }

        public void setDeparture_time(String departure_time) {
            this.departure_time = departure_time;
        }

        public String getDeparture_distance() {
            return departure_distance;
        }

        public void setDeparture_distance(String departure_distance) {
            this.departure_distance = departure_distance;
        }

        public static Live of(BusAndStopUpdateMessage.StopUpdateMessage message) {
            Live live1 = new Live();

            live1.setDeparture_time(message.getUpcomingTime());
            live1.setDeparture_distance(message.getDeparture_distance());
            return live1;
        }

    }
    private Live live;

    public Live getLive() {
        return live;
    }

    public void setLive(Live live) {
        this.live = live;
    }
}
