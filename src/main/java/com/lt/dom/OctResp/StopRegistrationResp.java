package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.oct.BusRoute;
import com.lt.dom.oct.BusStop;
import com.lt.dom.oct.CourseRatingKey;

import javax.persistence.*;


public
class StopRegistrationResp {


    @JsonBackReference
    @ManyToOne
    @MapsId("routeId")
    @JoinColumn(name = "route_id")
    BusRoute route;

    @JsonBackReference
    @ManyToOne
    @MapsId("stopId")
    @JoinColumn(name = "stop_id")
    BusStop stop;

    int rating;
    private String stopName;
    private Long id
            ;

    // standard constructors, getters, and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusRoute getRoute() {
        return route;
    }

    public void setRoute(BusRoute route) {
        this.route = route;
    }

    public BusStop getStop() {
        return stop;
    }

    public void setStop(BusStop stop) {
        this.stop = stop;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getStopName() {
        return stopName;
    }
}