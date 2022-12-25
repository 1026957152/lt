package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.BusStop;
import com.lt.dom.oct.PolylinePoint;
import com.lt.dom.proto.req.BusAndStopUpdateMessage;

import java.time.LocalTime;
import java.util.List;

//https://www3.septa.org/#/Real%20Time%20Data/trainView


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolylinePointResp extends BaseResp {
    private Double lat;
    private Double lng;
    private List places;
    private LocalTime departure_time;

    public static PolylinePointResp of(PolylinePoint e) {
        PolylinePointResp busStopResp = new PolylinePointResp();


        busStopResp.setLat(e.getLat());
        busStopResp.setLng(e.getLng());

        return busStopResp;
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

        public static PolylinePointResp.Live of(BusAndStopUpdateMessage.StopUpdateMessage message) {
            PolylinePointResp.Live live1 = new PolylinePointResp.Live();

            live1.setDeparture_time(message.getUpcomingTime());
            live1.setDeparture_distance(message.getDeparture_distance());
            return live1;
        }

    }
    private PolylinePointResp.Live live;

    public Live getLive() {
        return live;
    }

    public void setLive(Live live) {
        this.live = live;
    }
}
