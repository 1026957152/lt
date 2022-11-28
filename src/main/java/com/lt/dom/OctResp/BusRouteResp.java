package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BusVehicleReq;
import org.springframework.hateoas.EntityModel;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//https://www3.septa.org/#/Real%20Time%20Data/trainView



@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusRouteResp extends BaseResp {



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
    private List<EntityModel> stops;
    private List<BusVehicleResp> buses;

    public static BusRouteResp of(BusRoute e) {
        BusRouteResp busRouteResp = new BusRouteResp();
        busRouteResp.setCode(e.getCode());
        busRouteResp.setName(e.getName());
        busRouteResp.setShortName(e.getShortName());
        busRouteResp.setDescription(e.getDescription());

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

    public void setStops(List<EntityModel> stops) {
        this.stops = stops;
    }

    public List<EntityModel> getStops() {
        return stops;
    }


    public void withBuses(List<BusVehicle> stops) {

        this.setBuses(stops.stream().map(e->{
            BusVehicleResp busVehicleReq = BusVehicleResp.from(e);
            return busVehicleReq;
        }).collect(Collectors.toList()));
    }

    public void setBuses(List<BusVehicleResp> buses) {
        this.buses = buses;
    }

    public List<BusVehicleResp> getBuses() {
        return buses;
    }
}
