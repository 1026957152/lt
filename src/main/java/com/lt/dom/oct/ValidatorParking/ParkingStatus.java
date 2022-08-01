package com.lt.dom.oct.ValidatorParking;

import com.lt.dom.oct.ComponentVounch;

public class ParkingStatus {

    private ComponentVounch componentVounch;
    private String status;

    public ComponentVounch getComponentRightVounch() {
        return componentVounch;
    }

    public void setComponentRightVounch(ComponentVounch componentVounch) {
        this.componentVounch = componentVounch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
