package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumDeviceStatus;
import com.lt.dom.otcenum.EnumDeviceType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Device {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    private long id;

    @NotEmpty
    private String name;
    private String serialNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    private EnumDeviceType type;
    @NotNull
    private String project_id;
    private String device_model_id;
    @NotNull
    private long nickname;

    @NotNull
    private String id_;
    @NotNull
    private String manufacturer;
    @NotNull
    private boolean enabledOn;


    private LocalDateTime active_time;
    @NotNull
    private LocalDateTime update_time;

    @NotNull
    private LocalDateTime create_time;


    private long assetId;


    @NotNull
    private EnumDeviceStatus status;

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public boolean isEnabledOn() {
        return enabledOn;
    }

    public void setEnabledOn(boolean enabledOn) {
        this.enabledOn = enabledOn;
    }

    public LocalDateTime getActive_time() {
        return active_time;
    }

    public void setActive_time(LocalDateTime active_time) {
        this.active_time = active_time;
    }

    public LocalDateTime getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(LocalDateTime update_time) {
        this.update_time = update_time;
    }

    public LocalDateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }

    public long getAssetId() {
        return assetId;
    }

    public void setAssetId(long asset_id) {
        this.assetId = asset_id;
    }


    public EnumDeviceStatus getStatus() {
        return status;
    }

    public void setStatus(EnumDeviceStatus status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

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

    public long getNickname() {
        return nickname;
    }

    public void setNickname(long nickname) {
        this.nickname = nickname;
    }

    public String getId_() {
        return id_;
    }

    public void setId_(String id_) {
        this.id_ = id_;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }
}
