package com.lt.dom.OctResp;

import com.lt.dom.otcReq.TravelerReq;
import lombok.Builder;


import java.util.List;


public class ImportCVSBulkTravelerResp {

    int realNameCount;
    int notRealNameCount;
    List<TravelerReq> realnameTravelers;
    List<TravelerReq> notRealnameTravelers;

    public int getRealNameCount() {
        return realNameCount;
    }

    public void setRealNameCount(int realNameCount) {
        this.realNameCount = realNameCount;
    }

    public int getNotRealNameCount() {
        return notRealNameCount;
    }

    public void setNotRealNameCount(int notRealNameCount) {
        this.notRealNameCount = notRealNameCount;
    }

    public List<TravelerReq> getRealnameTravelers() {
        return realnameTravelers;
    }

    public void setRealnameTravelers(List<TravelerReq> realnameTravelers) {
        this.realnameTravelers = realnameTravelers;
    }

    public List<TravelerReq> getNotRealnameTravelers() {
        return notRealnameTravelers;
    }

    public void setNotRealnameTravelers(List<TravelerReq> notRealnameTravelers) {
        this.notRealnameTravelers = notRealnameTravelers;
    }
}
