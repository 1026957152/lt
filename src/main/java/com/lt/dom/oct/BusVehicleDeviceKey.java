package com.lt.dom.oct;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BusVehicleDeviceKey implements Serializable {

    @Column(name = "bus_id")
    Long busId;

    @Column(name = "device_id")
    Long deviceId;

    public BusVehicleDeviceKey(long busId, long deviceId) {
        this.busId = busId;
        this.deviceId = deviceId;
    }

    public BusVehicleDeviceKey() {

    }

    public Long getBusId() {
        return busId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}