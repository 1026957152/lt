package com.lt.dom.otcReq;


//https://developer.tuya.com/en/docs/cloud/52d5df4241?id=Kbn026xsi0cab

import java.time.LocalDateTime;

public class VisitorVehicleDelayPojo {  // 这个就是机器了啊
    private long visitor_car_id;

    private LocalDateTime begin_time;
    private LocalDateTime end_time;

    public long getVisitor_car_id() {
        return visitor_car_id;
    }

    public void setVisitor_car_id(long visitor_car_id) {
        this.visitor_car_id = visitor_car_id;
    }

    public LocalDateTime getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(LocalDateTime begin_time) {
        this.begin_time = begin_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }
}
