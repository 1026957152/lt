package com.lt.dom.oct;

import com.lt.dom.EnumTag;
import com.lt.dom.otcenum.EnumAssetQtStatus;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumAssetType_dynamic_static;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


//https://zubie.com/wp-content/zinc_v2.html?q=/developer/zinc_v2.html&#operation/createVehicle
@Entity
public class Vehicle extends Base{



    @NotNull
    private String group_keys;



    @Transient
    private List<EnumTag> tag_keys;

    private String schedule_key;

    private String fuel_type;  //unleaded
    private String fuel_economy;  //23.4

    private Integer odometer;

    private Integer odometer_um;


    private String primary_driver_key;


    private String vin;

    public String getGroup_keys() {
        return group_keys;
    }

    public void setGroup_keys(String group_keys) {
        this.group_keys = group_keys;
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

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
