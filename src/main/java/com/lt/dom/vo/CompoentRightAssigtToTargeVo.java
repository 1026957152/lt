package com.lt.dom.vo;


import com.lt.dom.oct.Pass;
import com.lt.dom.oct.Reservation;
import com.lt.dom.otcenum.EnumPlatform;


public class CompoentRightAssigtToTargeVo {


    private String orders_id;
    private String serial_no;
    private EnumPlatform platform;
    private Pass pass;
    private Reservation booking;


    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public String getOrders_id() {
        return orders_id;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setPlatform(EnumPlatform platform) {
        this.platform = platform;
    }

    public EnumPlatform getPlatform() {
        return platform;
    }

    public void setPass(Pass pass) {
        this.pass = pass;
    }

    public Pass getPass() {
        return pass;
    }

    public void setBooking(Reservation booking) {
        this.booking = booking;
    }

    public Reservation getBooking() {
        return booking;
    }
}
