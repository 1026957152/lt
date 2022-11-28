package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.otcenum.EnumSideOfStreetType;
import com.lt.dom.otcenum.EnumWayPointType;

import javax.persistence.*;

@Entity public class PlaceRegistration {

    @EmbeddedId
    BusStopPlaceKey id;


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
    @MapsId("placeId")
    @JoinColumn(name = "place_id")
    Place place;

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


    public BusStopPlaceKey getId() {
        return id;
    }

    public void setId(BusStopPlaceKey id) {
        this.id = id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place route) {
        this.place = route;
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