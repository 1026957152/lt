package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Base;

import com.lt.dom.oct.Museum;
import com.lt.dom.otcReq.LocationResp;

import java.time.LocalTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MuseumResp extends Base {

    private long supplier;
    private String name;
    private String name_long;

    private String desc_short;
    private String desc_long;
    private String slug;


    private LocalTime close_time;
    private LocalTime open_time;


    private PhotoResp thumbnail_image;
    private String guestServicesPhoneNumber;
    private String code;
    private PhotoResp header;

    private LocationResp address;
    private List exhibitions;

    public static MuseumResp from(Museum e) {
        MuseumResp museumResp = new MuseumResp();
        museumResp.setClose_time(e.getClose_time());
        museumResp.setOpen_time(e.getOpen_time());
        museumResp.setName(e.getName());
        museumResp.setDesc_short(e.getDesc_short());
        museumResp.setGuestServicesPhoneNumber(e.getGuestServicesPhoneNumber());
        museumResp.setCode(e.getCode());

        return museumResp;
    }

    public void setThumbnail_image(PhotoResp thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
    }

    public PhotoResp getThumbnail_image() {
        return thumbnail_image;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_long() {
        return name_long;
    }

    public void setName_long(String name_long) {
        this.name_long = name_long;
    }

    public String getDesc_short() {
        return desc_short;
    }

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }

    public String getDesc_long() {
        return desc_long;
    }

    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public LocalTime getClose_time() {
        return close_time;
    }

    public void setClose_time(LocalTime close_time) {
        this.close_time = close_time;
    }

    public LocalTime getOpen_time() {
        return open_time;
    }

    public void setOpen_time(LocalTime open_time) {
        this.open_time = open_time;
    }

    public void setGuestServicesPhoneNumber(String guestServicesPhoneNumber) {
        this.guestServicesPhoneNumber = guestServicesPhoneNumber;
    }

    public String getGuestServicesPhoneNumber() {
        return guestServicesPhoneNumber;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setHeader(PhotoResp header) {
        this.header = header;
    }

    public PhotoResp getHeader() {
        return header;
    }

    public void setExhibitions(List exhibitions) {

        this.exhibitions = exhibitions;
    }

    public List getExhibitions() {
        return exhibitions;
    }

    public void setAddress(LocationResp address) {
        this.address = address;
    }

    public LocationResp getAddress() {
        return address;
    }
}
