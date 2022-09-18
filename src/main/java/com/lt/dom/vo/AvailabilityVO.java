package com.lt.dom.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.otcenum.EnumAvailabilityStatus;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvailabilityVO {


    @NotNull
    private Boolean bookable ;

    private EnumAvailabilityStatus status;

    List<OpeningHour> openingHours;
    private int spaces_remaining;
    private boolean available;
    private int capacity;
    private String week_text;
    private String booking_at_text;

    public EnumAvailabilityStatus getStatus() {
        return status;
    }

    public void setStatus(EnumAvailabilityStatus status) {
        this.status = status;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    //  A boolean value (true or false) indicating whether you're able to sell tickets. This is basically just an alias for: status == 'AVAILABLE' || status == 'LIMITED'
  //  capacity
    public List<OpeningHour> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(List<OpeningHour> openingHours) {
        this.openingHours = openingHours;
    }

    private String booking_at;

    public void setSpaces_remaining(int spaces_remaining) {
        this.spaces_remaining = spaces_remaining;
    }

    public int getSpaces_remaining() {
        return spaces_remaining;
    }

    public void setWeek_text(String week_text) {
        this.week_text = week_text;
    }

    public String getWeek_text() {
        return week_text;
    }

    public void setBooking_at_text(String booking_at_text) {
        this.booking_at_text = booking_at_text;
    }

    public String getBooking_at_text() {
        return booking_at_text;
    }

    public static class OpeningHour {
        private LocalTime from;//	string the earliest time bookings are allowed, in 24 hour format eg 09:00
        private LocalTime to;//

        public LocalTime getFrom() {
            return from;
        }

        public void setFrom(LocalTime from) {
            this.from = from;
        }

        public LocalTime getTo() {
            return to;
        }

        public void setTo(LocalTime to) {
            this.to = to;
        }
    }

    public void setBooking_at(String booking_at) {

        this.booking_at = booking_at;
    }

    public String getBooking_at() {
        return booking_at;
    }

    public Boolean getBookable() {
        return bookable;
    }

    public void setBookable(Boolean bookable) {
        this.bookable = bookable;
    }




}
