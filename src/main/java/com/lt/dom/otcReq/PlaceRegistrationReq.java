package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.oct.BusStop;
import com.lt.dom.oct.BusStopPlaceKey;
import com.lt.dom.oct.Place;
import com.lt.dom.otcenum.EnumSideOfStreetType;
import com.lt.dom.otcenum.EnumWayPointType;

import javax.persistence.*;

public class PlaceRegistrationReq {



    public EnumSideOfStreetType sideOfStreetType;
    public EnumWayPointType type;
    private Long id;


    public EnumWayPointType getType() {
        return type;
    }

    public void setType(EnumWayPointType type) {
        this.type = type;
    }


    int rating;





    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }




    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}