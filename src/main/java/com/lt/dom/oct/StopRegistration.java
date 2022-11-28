package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.otcenum.EnumSideOfStreetType;
import com.lt.dom.otcenum.EnumWayPointType;

import javax.persistence.*;

@Entity
public
class StopRegistration {

    @EmbeddedId
    CourseRatingKey id;


    public EnumSideOfStreetType sideOfStreetType;
    public EnumWayPointType type;



    public EnumWayPointType getType() {
        return type;
    }

    public void setType(EnumWayPointType type) {
        this.type = type;
    }

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





    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    // standard constructors, getters, and setters


    public CourseRatingKey getId() {
        return id;
    }

    public void setId(CourseRatingKey id) {
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
}