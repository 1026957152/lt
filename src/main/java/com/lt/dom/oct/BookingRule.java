package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumAvailabilityRangetype;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

@Entity
public class BookingRule {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    private long product;
    private EnumAvailabilityRangetype rangetype;
    private Boolean bookable;
    private Integer priority;
    private DayOfWeek week_from;
    private DayOfWeek week_to;
    private Month month_from;//	string the earliest time bookings are allowed, in 24 hour format eg 09:00
    private Month month_to;//	string the latest time bookings are allowed, in 24 hour format eg 23:00
    private String months_json;
    private String weeks_json;


    private String minimum_advance_reservation;

    private String maximum_advance_reservation;

    private String limit_per_booking;

    private Boolean require_identity;



    public Month getMonth_from() {
        return month_from;
    }

    public void setMonth_from(Month month_from) {
        this.month_from = month_from;
    }

    public Month getMonth_to() {
        return month_to;
    }

    public void setMonth_to(Month month_to) {
        this.month_to = month_to;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProduct() {
        return product;
    }

    public void setProduct(long productId) {
        this.product = productId;
    }

    private LocalDate bookings_from;//	string the earliest time bookings are allowed, in 24 hour format eg 09:00
    private LocalDate bookings_to;//	string the latest time bookings are allowed, in 24 hour format eg 23:00

    private LocalTime time_from;//	string the earliest time bookings are allowed, in 24 hour format eg 09:00
    private LocalTime time_to;//	string the latest time bookings are allowed, in 24 hour format eg 23:00

    public LocalTime getTime_from() {
        return time_from;
    }

    public void setTime_from(LocalTime time_from) {
        this.time_from = time_from;
    }

    public LocalTime getTime_to() {
        return time_to;
    }

    public void setTime_to(LocalTime time_to) {
        this.time_to = time_to;
    }

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

    public void setRangetype(EnumAvailabilityRangetype rangtype) {

        this.rangetype = rangtype;
    }

    public EnumAvailabilityRangetype getRangetype() {
        return rangetype;
    }

    public Boolean getBookable() {
        return bookable;
    }

    public void setBookable(Boolean bookable) {
        this.bookable = bookable;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setWeek_from(DayOfWeek week_from) {
        this.week_from = week_from;
    }

    public DayOfWeek getWeek_from() {
        return week_from;
    }

    public void setWeek_to(DayOfWeek week_to) {
        this.week_to = week_to;
    }

    public DayOfWeek getWeek_to() {
        return week_to;
    }

    public void setMonths_json(String months_json) {
        this.months_json = months_json;
    }

    public String getMonths_json() {
        return months_json;
    }

    public void setWeeks_json(String weeks_json) {
        this.weeks_json = weeks_json;
    }

    public String getWeeks_json() {
        return weeks_json;
    }
}
