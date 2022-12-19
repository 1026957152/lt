package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumWayPointType;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//https://www3.septa.org/#/Real%20Time%20Data/trainView


@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusRouteEditReq extends BaseResp {
    private EntityModel busTab;
    private EntityModel stopTab;

    public void setBusTab(EntityModel busTab) {
        this.busTab = busTab;
    }

    public EntityModel getBusTab() {
        return busTab;
    }

    public void setStopTab(EntityModel stopTab) {
        this.stopTab = stopTab;
    }

    public EntityModel getStopTab() {
        return stopTab;
    }

    public static class BusTab {
        public static class BusReq {


            private Long id;

            public void setId(Long id) {
                this.id = id;
            }

            public Long getId() {
                return id;
            }
        }
        private List<BusReq> buses;

        public static BusTab of(List<BusVehicle> buses) {

            BusTab stopTab = new BusTab();

            stopTab.setBuses(buses.stream().map(e->{
                BusReq busVehicleReq = new BusReq();
                busVehicleReq.setId(e.getId());
                return busVehicleReq;
            }).collect(Collectors.toList()));
            return stopTab;
        }

        public List<BusReq> getBuses() {
            return buses;
        }

        public void setBuses(List<BusReq> buses) {
            this.buses = buses;
        }

    }

    public static class StopTab {
        public static class StopRegistrationReq {

            private Long id;
            private String lable;
            private String label;
            private EnumWayPointType type;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            private String name;
            private Double lat;//Trip was soft deleted and should not be displayed.
            private Double lng;//Trip was soft deleted and should not be displayed.
            private Long supplier;
            private String description;
            private String shortName;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public Long getSupplier() {
                return supplier;
            }

            public void setSupplier(Long supplier) {
                this.supplier = supplier;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getShortName() {
                return shortName;
            }

            public void setShortName(String shortName) {
                this.shortName = shortName;
            }

            public static StopRegistrationReq of(StopRegistration stopRegistration) {

                StopRegistrationReq stopRegistrationReq = new StopRegistrationReq();
                stopRegistrationReq.setLng(stopRegistration.getStop().getLng());
                stopRegistrationReq.setLat(stopRegistration.getStop().getLat());
                stopRegistrationReq.setLabel(stopRegistration.getLabel());
                stopRegistrationReq.setType(stopRegistration.getType());
                return stopRegistrationReq;

            }

            public String getLable() {
                return lable;
            }

            public void setLable(String lable) {
                this.lable = lable;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getLabel() {
                return label;
            }

            public void setType(EnumWayPointType type) {
                this.type = type;
            }

            public EnumWayPointType getType() {
                return type;
            }
        }


        private List<StopRegistrationReq> stops;

        public static StopTab of(List<StopRegistration> stops) {
            StopTab stopTab1 = new StopTab();
            stopTab1.setStops(stops.stream().map(e->{
                StopRegistrationReq busStopReq = StopRegistrationReq.of(e);

                return busStopReq;
            }).collect(Collectors.toList()));
            return stopTab1;
        }

        public List<StopRegistrationReq> getStops() {
            return stops;
        }

        public void setStops(List<StopRegistrationReq> stops) {
            this.stops = stops;
        }



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


    public static BusRouteEditReq of(BusRoute e) {
        BusRouteEditReq busRouteResp = new BusRouteEditReq();
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




}
