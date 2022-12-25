package com.lt.dom.proto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.BaseResp;
import com.lt.dom.OctResp.BusVehicleResp;
import com.lt.dom.oct.BusRoute;


import java.time.LocalDateTime;
import java.util.List;

//https://www3.septa.org/#/Real%20Time%20Data/trainView


@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusRouteProto extends BaseResp {



    private String code;

    private String route_direction;
    private String reason;
    private String current_message;
    private String start_location;
    private String end_location;
    private LocalDateTime start_date_time;
    private LocalDateTime end_date_time;
    private Long supplier;
    private String shortName;
    private String name;
    private String description;
    private List<BusStopRespProto> stops;
    private List<BusVehicleResp> buses;

    private String websocketUrl;
    private String websocketDestination;
    private List polylinePoints;

    public static BusRouteProto of(BusRoute e) {
        BusRouteProto busRouteResp = new BusRouteProto();
        busRouteResp.setCode(e.getCode());
        busRouteResp.setName(e.getName());
        busRouteResp.setShortName(e.getShortName());
        busRouteResp.setDescription(e.getDescription());
        busRouteResp.setCreatedDate(e.getCreatedDate());
        busRouteResp.setModifiedDate(e.getModifiedDate());
        return busRouteResp;
    }


    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }

    public Long getSupplier() {
        return supplier;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setStops(List<BusStopRespProto> stops) {
        this.stops = stops;
    }

    public List<BusStopRespProto> getStops() {
        return stops;
    }


    public void withBuses(List stops) {

        this.setBuses(stops);
    }

    public void setBuses(List<BusVehicleResp> buses) {
        this.buses = buses;
    }

    public List<BusVehicleResp> getBuses() {
        return buses;
    }



    public void setWebsocketUrl(String websocketUrl) {
        this.websocketUrl = websocketUrl;
    }

    public String getWebsocketUrl() {
        return websocketUrl;
    }

    public void setWebsocketDestination(String websocketDestination) {
        this.websocketDestination = websocketDestination;
    }

    public String getWebsocketDestination() {
        return websocketDestination;
    }

    public <R> void setPolylinePoints(List polylinePoints) {
        this.polylinePoints = polylinePoints;
    }

    public List getPolylinePoints() {
        return polylinePoints;
    }
}
