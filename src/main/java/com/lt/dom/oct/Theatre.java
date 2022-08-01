package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumRedemptionMethod;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;


@Entity
public class Theatre {

    @Id
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

   // private int[][] seats;//	A 2d array, representing the available seating in the theater
    private int seatsAvailable;//	The total number of seats in the theater










    private String name;//	Name of the theater
    private boolean deliveryToSeat;//"deliveryToSeat": true,
    private String longName;//        "longName": "AMC Streets Of St Charles 8",
    private int guestServicesPhoneNumber;//          "guestServicesPhoneNumber": "636-352-0123",
    private int utcOffset;//          "utcOffset": "-05:00",
    private String  timezone;//          "timezone": "CENTRAL TIME",
    private int slug;//         "slug": "amc-streets-of-st-charles-8",
    private String facebookUrl;//         "facebookUrl": "https://www.facebook.com/AMCStreetsOfStCharles8",
    private String outageDescription;//          "outageDescription": "This theatre is temporarily closed in accordance with local, state and federal guidelines. It will re-open when those guidelines allow. Please continue to check back here for updates.",
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
    @Transient
    private Location location;//

    @Transient
    private Media media;
}
