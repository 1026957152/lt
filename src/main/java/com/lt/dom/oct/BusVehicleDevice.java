package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class BusVehicleDevice extends BaseWithoutId{

    @EmbeddedId
    BusVehicleDeviceKey id;


    @JsonBackReference
    @ManyToOne
    @MapsId("busId")
    @JoinColumn(name = "bus_id")
    BusVehicle busVehicle;

    @JsonBackReference
    @ManyToOne
    @MapsId("deviceId")
    @JoinColumn(name = "device_id")
    Device device;



    private Float net; // 分销价
    private Long sku;
    private Float original; //票面原价
    private Float market; // 市场价

    public BusVehicle getBusVehicle() {
        return busVehicle;
    }

    public void setBusVehicle(BusVehicle busVehicle) {
        this.busVehicle = busVehicle;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    private String code;

    private String name;

    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BusVehicleDeviceKey getId() {
        return id;
    }

    public void setId(BusVehicleDeviceKey id) {
        this.id = id;
    }


    public void setNet(Float net) {

        this.net = net;
    }

    public Float getNet() {
        return net;
    }

    public void setSku(Long sku) {
        this.sku = sku;
    }

    public Long getSku() {
        return sku;
    }

    public Float getOriginal() {
        return original;
    }

    public void setOriginal(Float original) {
        this.original = original;
    }

    public Float getMarket() {
        return market;
    }

    public void setMarket(Float market) {
        this.market = market;
    }
}
