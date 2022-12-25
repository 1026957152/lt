package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.otcenum.EnumSideOfStreetType;
import com.lt.dom.otcenum.EnumWayPointType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;

@Entity
public class PolylinePoint extends Base{


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "route_id")
    BusRoute route;
    private Double lat;
    private Double lng;

    public BusRoute getRoute() {
        return route;
    }

    public void setRoute(BusRoute route) {
        this.route = route;
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
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}