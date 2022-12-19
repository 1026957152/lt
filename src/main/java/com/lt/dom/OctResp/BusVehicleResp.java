package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.EnumTag;
import com.lt.dom.proto.req.BusAndStopUpdateMessage;
import com.lt.dom.oct.BusVehicle;


import java.util.List;


//https://zubie.com/wp-content/zinc_v2.html?q=/developer/zinc_v2.html&#operation/createVehicle


@JsonInclude(JsonInclude.Include.NON_NULL)
public class BusVehicleResp extends BaseResp{



    public static class Live {

        private Double lat;
        private Double lng;

        public static Live of(BusAndStopUpdateMessage.BusUpdateMessage message) {
            Live live1 = new Live();
            live1.setLat(message.getLat());
            live1.setLng(message.getLng());
            return live1;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLat() {
            return lat;
        }

        public void setLng(Double lng) {
            this.lng = lng;
        }

        public Double getLng() {
            return lng;
        }
    }
    private Live live;

    public Live getLive() {
        return live;
    }

    public void setLive(Live live) {
        this.live = live;
    }

    private List<EnumTag> tag_keys;

    private String schedule_key;

    private String fuel_type;  //unleaded
    private String fuel_economy;  //23.4

    private Integer odometer;

    private Integer odometer_um;


    private String primary_driver_key;


    private String plateName;
    private String code;

    public static BusVehicleResp from(BusVehicle e) {
        BusVehicleResp busVehicleResp = new BusVehicleResp();
        busVehicleResp.setPlateName(e.getPlateName());
        busVehicleResp.setCode(e.getCode());
        busVehicleResp.setCreatedDate(e.getCreatedDate());
        busVehicleResp.setModifiedDate(e.getModifiedDate());
        return busVehicleResp;
    }


    public List<EnumTag> getTag_keys() {
        return tag_keys;
    }

    public void setTag_keys(List<EnumTag> tag_keys) {
        this.tag_keys = tag_keys;
    }

    public String getSchedule_key() {
        return schedule_key;
    }

    public void setSchedule_key(String schedule_key) {
        this.schedule_key = schedule_key;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public String getFuel_economy() {
        return fuel_economy;
    }

    public void setFuel_economy(String fuel_economy) {
        this.fuel_economy = fuel_economy;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public void setOdometer(Integer odometer) {
        this.odometer = odometer;
    }

    public Integer getOdometer_um() {
        return odometer_um;
    }

    public void setOdometer_um(Integer odometer_um) {
        this.odometer_um = odometer_um;
    }

    public String getPrimary_driver_key() {
        return primary_driver_key;
    }

    public void setPrimary_driver_key(String primary_driver_key) {
        this.primary_driver_key = primary_driver_key;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public void setCode(String code) {

        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
