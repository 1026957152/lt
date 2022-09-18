package com.lt.dom.vo;

import com.lt.dom.otcenum.EnumDeviceStatus;
import com.lt.dom.otcenum.EnumDeviceType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

//https://developer.tuya.com/en/docs/cloud/7d3f13ae55?id=Kb2rzcwpmvaci
public class DeviceVo {


    @NotNull
    private EnumDeviceType type;
    @NotNull
    private String project_id;
    private String device_model_id;

    @NotNull
    private String uuid;
    @NotNull
    private String manufacturer;
    @NotNull
    private boolean enabledOn;


    @NotNull
    private LocalDateTime active_time;
    @NotNull
    private LocalDateTime update_time;

    @NotNull
    private LocalDateTime create_time;


    @NotNull
    private long asset_id;

    @NotNull
    private long owner_id;
    @NotNull
    private boolean online;
    @NotNull
    private String icon;


    @NotNull
    private EnumDeviceStatus status;

    public EnumDeviceType getType() {
        return type;
    }

    public void setType(EnumDeviceType type) {
        this.type = type;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getDevice_model_id() {
        return device_model_id;
    }

    public void setDevice_model_id(String device_model_id) {
        this.device_model_id = device_model_id;
    }

}
