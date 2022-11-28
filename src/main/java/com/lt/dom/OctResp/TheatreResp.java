package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.lt.dom.oct.Address;
import com.lt.dom.oct.Attribute;

import com.lt.dom.oct.Media;
import com.lt.dom.oct.Theatre;
import com.lt.dom.otcReq.LocationResp;
import com.lt.dom.otcenum.EnumRedemptionMethod;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TheatreResp extends BaseResp {


    private long supplier;
    private String code;

   // private int[][] seats;//	A 2d array, representing the available seating in the theater
    private int seatsAvailable;//	The total number of seats in the theater












    private String name;//	Name of the theater
    private String longName;//        "longName": "AMC Streets Of St Charles 8",

    private String shortdesc;//        "longName": "AMC Streets Of St Charles 8",
    private String longdesc;//        "longName": "AMC Streets Of St Charles 8",
    private int slug;//         "slug": "amc-streets-of-st-charles-8",
    private Boolean closed;//          "utcOffset": "-05:00",
    private String guestServicesPhoneNumber;//          "guestServicesPhoneNumber": "636-352-0123",
    private String outageDescription;//          "outageDescription": "This theatre is temporarily closed in accordance with local, state and federal guidelines. It will re-open when those guidelines allow. Please continue to check back here for updates.",

    private String redemptionMethods_json;//          "outageDescription": "This theatre is temporarily closed in accordance with local, state and federal guidelines. It will re-open when those guidelines allow. Please continue to check back here for updates.",
    private List<EnumRedemptionMethod> redemptionMethods;

    private PhotoResp cover;
    private LocationResp address;

    public static TheatreResp simple(Theatre e) {
        TheatreResp theatreResp = new TheatreResp();


        theatreResp.setName(e.getName());

        theatreResp.setShortdesc(e.getShortdesc());
        theatreResp.setGuestServicesPhoneNumber(e.getGuestServicesPhoneNumber());
        return theatreResp;
    }
    public static TheatreResp of(Theatre e) {
        TheatreResp theatreResp = new TheatreResp();
        theatreResp.setCreatedDate(e.getCreatedDate());
        theatreResp.setModifiedDate(e.getModifiedDate());

        theatreResp.setCode(e.getCode());
        theatreResp.setName(e.getName());
        theatreResp.setLongName(e.getLongName());
        theatreResp.setShortdesc(e.getShortdesc());
        theatreResp.setGuestServicesPhoneNumber(e.getGuestServicesPhoneNumber());
        return theatreResp;
    }

    public static TheatreResp from(Theatre theatre) {
        TheatreResp theatreResp = of(theatre);
        if(theatre.getRedemptionMethods_json() != null){
            theatreResp.setRedemptionMethods(Arrays.stream(new Gson().fromJson(theatre.getRedemptionMethods_json(),EnumRedemptionMethod[].class)).toList());

        }
        return theatreResp;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getLongdesc() {
        return longdesc;
    }

    public void setLongdesc(String longdesc) {
        this.longdesc = longdesc;
    }

    public int getSlug() {
        return slug;
    }

    public void setSlug(int slug) {
        this.slug = slug;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public String getGuestServicesPhoneNumber() {
        return guestServicesPhoneNumber;
    }

    public void setGuestServicesPhoneNumber(String guestServicesPhoneNumber) {
        this.guestServicesPhoneNumber = guestServicesPhoneNumber;
    }

    public String getOutageDescription() {
        return outageDescription;
    }

    public void setOutageDescription(String outageDescription) {
        this.outageDescription = outageDescription;
    }

    public String getRedemptionMethods_json() {
        return redemptionMethods_json;
    }

    public void setRedemptionMethods_json(String redemptionMethods_json) {
        this.redemptionMethods_json = redemptionMethods_json;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
/*
    public int[][] getSeats() {
        return seats;
    }

    public void setSeats(int[][] seats) {
        this.seats = seats;
    }*/

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }





    @Transient
    private List<EnumRedemptionMethod> redemptionMethodList;
    @Transient
    private List<Attribute> attributes;
    @Transient
    private Address location;//

    @Transient
    private Media media;

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setRedemptionMethods(List<EnumRedemptionMethod> redemptionMethods) {
        this.redemptionMethods = redemptionMethods;
    }

    public List<EnumRedemptionMethod> getRedemptionMethods() {
        return redemptionMethods;
    }



    public void setCover(PhotoResp cover) {
        this.cover = cover;
    }

    public PhotoResp getCover() {
        return cover;
    }

    public void setAddress(LocationResp address) {
        this.address = address;
    }

    public LocationResp getAddress() {
        return address;
    }
}
