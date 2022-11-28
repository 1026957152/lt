package com.lt.dom.oct;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//https://www3.septa.org/#/Real%20Time%20Data/trainView
@Entity
public class BusRouteStop extends Base{



    @Transient
    private LinkedList<Integer> distance;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "Route_stations")
    private List<BusStop> stops = new ArrayList<>();

    public List<BusStop> getStops() {
        return stops;
    }

    public void setStops(List<BusStop> stops) {
        this.stops = stops;
    }

    public void addStationInList(BusStop addst) {
        stops.add(addst);
    }

    public void addAfterStation(BusStop station1, BusStop addStation) {
        int index = this.stops.indexOf(station1);
        this.stops.add(index, addStation);
    }

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
}
