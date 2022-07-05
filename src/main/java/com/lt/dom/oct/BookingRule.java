package com.lt.dom.oct;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class BookingRule {

    @Id
    private long id;

    private long productId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    private LocalDate bookings_from;//	string the earliest time bookings are allowed, in 24 hour format eg 09:00
    private LocalDate bookings_to;//	string the latest time bookings are allowed, in 24 hour format eg 23:00

    public LocalDate getBookings_from() {
        return bookings_from;
    }

    public void setBookings_from(LocalDate bookings_from) {
        this.bookings_from = bookings_from;
    }

    public LocalDate getBookings_to() {
        return bookings_to;
    }

    public void setBookings_to(LocalDate bookings_to) {
        this.bookings_to = bookings_to;
    }

    private boolean booking_available;//	boolean Whether or not a booking would be allowed, based on the Can Book checkbox in Collins
    private int max_duration;//	integer The maximum duration for a booking in minutes *
    private String booking_notes;//	string The ‘Bookings Policy’ message
    private int min_people;//	number The minumum number of guests allowed *
    private int max_people;//	number The maximum number of guests allowed *
    private String bookings_shut;//	string The time when same day bookings close *

}
