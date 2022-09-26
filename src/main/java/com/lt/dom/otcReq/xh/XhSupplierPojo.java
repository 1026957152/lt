package com.lt.dom.otcReq.xh;

import com.lt.dom.otcenum.EnumBussinessType;
import com.lt.dom.otcenum.EnumSupplierType;

import javax.validation.constraints.NotNull;

public class XhSupplierPojo {


    private String firstName;//	string	The user’s first name
    private String lastName;//	string	The user’s last name
    private String phone;//	string	The user’s last name

    @NotNull
    private String supplierName;// 公司名称	Display name for the suplier

    @NotNull
    private EnumSupplierType type;//  运营商， 售票者

 //   @Pattern(regexp = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$")
   // private String location;//  运营商， 售票者
    private String shortDesc;//	string	The user’s first name
    private String desc;
    private float lat;
    private float lng;
    private String region;
    private String state;
    private String locality;
    private String street;

    @NotNull
    private EnumBussinessType business_type;
    private String location;
    private String locationName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public EnumSupplierType getType() {
        return type;
    }

    public void setType(EnumSupplierType type) {
        this.type = type;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getLocality() {
        return locality;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }

    public EnumBussinessType getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(EnumBussinessType business_type) {
        this.business_type = business_type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }


    private Merchant merchant;

    public static class Merchant {
        String business_license;

    }


    private TravelAgency TravelAgency;

    public static class TravelAgency {
        String business_license;
        String license;
        String liability_insurance;

    }
}
