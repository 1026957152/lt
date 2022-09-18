package com.lt.dom.otcReq;


//https://developer.tuya.com/en/docs/cloud/52d5df4241?id=Kbn026xsi0cab

import java.time.LocalDateTime;

public class ActivateDevicePojo {  // 这个就是机器了啊
    private long device_id;
    private long asset_id;
    private long uid;

    private String lat; //latitude
    private String lon; //longitude
    private long visitor_car_id;

    public long getVisitor_car_id() {
        return visitor_car_id;
    }

    public void setVisitor_car_id(long visitor_car_id) {
        this.visitor_car_id = visitor_car_id;
    }

}
