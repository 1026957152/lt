package com.lt.dom.vo;

import com.lt.dom.otcReq.SupplierPojo;
import com.lt.dom.otcenum.EnumBussinessType;
import com.lt.dom.otcenum.EnumSupplierType;

import javax.validation.constraints.NotNull;

public class SupplierPojoVo {


    @NotNull
    private String supplierName;//

    @NotNull
    private EnumSupplierType type;//  运营商， 售票者

    private String desc;
    private float lat;
    private float lng;


    @NotNull
    private EnumBussinessType business_type;
    private String location;
    private String locationName;

    public static SupplierPojoVo fromPojo(SupplierPojo supplierPojo) {
        SupplierPojoVo supplierPojoVo = new SupplierPojoVo();
        supplierPojoVo.setDesc(supplierPojo.getDesc());
        supplierPojoVo.setType(supplierPojo.getType());
        supplierPojoVo.setBusiness_type(supplierPojo.getBusiness_type());
        supplierPojoVo.setBusiness_type(supplierPojo.getBusiness_type());
        supplierPojoVo.setLocation(supplierPojo.getLocation());
        supplierPojoVo.setLocationName(supplierPojo.getLocationName());
        supplierPojoVo.setLat(Float.valueOf(supplierPojo.getLat()));
        supplierPojoVo.setLng(Float.valueOf(supplierPojo.getLng()));
        supplierPojoVo.setSupplierName(supplierPojo.getSupplierName());
        return supplierPojoVo;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public EnumSupplierType getType() {
        return type;
    }

    public void setType(EnumSupplierType type) {
        this.type = type;
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

    public EnumBussinessType getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(EnumBussinessType business_type) {
        this.business_type = business_type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
