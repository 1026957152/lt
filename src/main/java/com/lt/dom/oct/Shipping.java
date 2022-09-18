package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumPassShippingStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Shipping {
    @Version
    private Integer version;

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    private long id;

    private long booking;
    private long product;


    private String address;
    private String carrier;
    private String name;
    private String phone;
    private EnumPassShippingStatus status;
    private String tracking_number;

    public long getBooking() {
        return booking;
    }

    public void setBooking(long booking) {
        this.booking = booking;
    }

    public long getProduct() {
        return product;
    }

    public void setProduct(long product) {
        this.product = product;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EnumPassShippingStatus getStatus() {
        return status;
    }

    public void setStatus(EnumPassShippingStatus status) {
        this.status = status;
    }

    public String getTracking_number() {
        return tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }
}
