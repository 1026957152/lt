package com.lt.dom.OctResp.home;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.hateoas.EntityModel;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeBusResp {


    private List lines;
    private List<EntityModel> availableFares;
    private List buses;
    private List stops;
    private String websocketUrl;
    private String websocketDestination;


    public <T> void setLines(List lines) {
        this.lines = lines;
    }

    public List getLines() {
        return lines;
    }

    public void setAvailableFares(List<EntityModel> availableFares) {
        this.availableFares = availableFares;
    }

    public List<EntityModel> getAvailableFares() {
        return availableFares;
    }


    public <R> void setBuses(List buses) {
        this.buses = buses;
    }

    public List getBuses() {
        return buses;
    }

    public <R> void setStops(List stops) {
        this.stops = stops;
    }

    public List getStops() {
        return stops;
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
}
