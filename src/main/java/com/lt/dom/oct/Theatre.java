package com.lt.dom.oct;

import com.lt.dom.OctResp.EnumLongIdResp;
import com.lt.dom.otcenum.EnumRedemptionMethod;
import org.apache.commons.math3.analysis.function.Add;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


@Entity
public class Theatre extends Base{


    private long supplier;
    private String code;



   // private int[][] seats;//	A 2d array, representing the available seating in the theater
    private int seatsAvailable;//	The total number of seats in the theater












    private String name;//	Name of the theater
    private String longName;//        "longName": "AMC Streets Of St Charles 8",

    private String shortdesc;//        "longName": "AMC Streets Of St Charles 8",
    private String longdesc;//        "longName": "AMC Streets Of St Charles 8",
    private String slug;//         "slug": "amc-streets-of-st-charles-8",
    private Boolean closed;//          "utcOffset": "-05:00",
    private String guestServicesPhoneNumber;//          "guestServicesPhoneNumber": "636-352-0123",
    private String outageDescription;//          "outageDescription": "This theatre is temporarily closed in accordance with local, state and federal guidelines. It will re-open when those guidelines allow. Please continue to check back here for updates.",

    private String redemptionMethods_json;//          "outageDescription": "This theatre is temporarily closed in accordance with local, state and federal guidelines. It will re-open when those guidelines allow. Please continue to check back here for updates.",

    public static List List(List<Theatre> componentRightMap) {
        return componentRightMap.stream().map(x->{

            EnumLongIdResp enumResp = new EnumLongIdResp();
            enumResp.setId(x.getId());

            enumResp.setText(x.getName()+"_"+x.getCode());
            return enumResp;
        }).collect(Collectors.toList());
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
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

    private boolean deliveryToSeat;//"deliveryToSeat": true,
    private int utcOffset;//          "utcOffset": "-05:00",
    private String  timezone;//          "timezone": "CENTRAL TIME",
    private String facebookUrl;//         "facebookUrl": "https://www.facebook.com/AMCStreetsOfStCharles8",
    private String websiteUrl;//         "websiteUrl": "https://www.amctheatres.com/movie-theatres/st-louis/amc-streets-of-st-charles-8",
    private String ticketable;//         "ticketable": "AMC",







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



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location", referencedColumnName = "id")
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

    public void setLocation(Address location) {

        this.location = location;
    }

    public Address getLocation() {
        return location;
    }
}
