package com.lt.dom.oct;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

//https://www3.septa.org/#/Real%20Time%20Data/trainView
@Entity
public class BusRoute extends Base{


    private String icon;


    @Transient
    private LinkedList<Integer> distance;

    public List<BusVehicle> getBuses() {
        return buses;
    }

    @OneToMany(fetch = FetchType.LAZY,cascade = { CascadeType.ALL },mappedBy="route")

//	@Nullable
    private List<BusVehicle> buses;

    public void setBuses(List<BusVehicle> buses) {
        this.buses = buses;
    }
    /*
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "Route_stations")
    private List<BusStop> stops = new ArrayList<>();
*/




    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY,    cascade = CascadeType.ALL)
    List<StopRegistration> registrations;


    public List<StopRegistration> getStops() {
        return registrations;
    }

    public void setStops(List<StopRegistration> stops) {
        this.registrations = stops;
    }

    public void addStationInList(StopRegistration addst) {
        registrations.add(addst);
    }



    public void addAfterStation(StopRegistration station1, StopRegistration addStation) {
        int index = this.registrations.indexOf(station1);
        this.registrations.add(index, addStation);
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

    public List<StopRegistration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<StopRegistration> registrations) {
        this.registrations = registrations;
    }
}
