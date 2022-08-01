package com.lt.dom.oct;

import java.util.List;

public class TheaterSession {

    private long theaterId;//	The Id of the theater the session belongs to
    private String name;//	The name of the show
    private long description;//	The description of the show
    private long start;//	When does the show start
    private long end;//	When does the show end
    private long price;//	The price of the show
    private long seatsAvailable;//	The number of seats left in this session
    private int[][] seats;//	The 2d array seat map, showing which seats have been booked
    private List<TheaterReservation> reservations;//	Any current reservations for this session that are in a cart

    public long getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(long theaterId) {
        this.theaterId = theaterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDescription() {
        return description;
    }

    public void setDescription(long description) {
        this.description = description;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(long seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public int[][] getSeats() {
        return seats;
    }

    public void setSeats(int[][] seats) {
        this.seats = seats;
    }

    public List<TheaterReservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<TheaterReservation> reservations) {
        this.reservations = reservations;
    }
}
