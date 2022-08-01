package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tour {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @JsonProperty("id")
    private long id;


    private String tour_name;//	Tour name - Short (Max. 50 characters)
    private String tour_name_long;//	Tour name - Long (Max. 100 characters)
    private String time_type;//	Purely for display purposes currently, indicates to end users whether the times loaded are tour start and end times, opening hours or entry times with flexible ends.

        //    strict - Tour start and end times (Default)
    private String opening_hours;// - Opening Hours
    private String strict_start;// - Entry time with flexible duration
    private String start_time;//	Start time for the Tour

    //May be a 24 hour time value in local time (e.g. "09:00").

    //If there are multiple start times per day or the start time varies by day then the value will be "MULTI"
    private String end_time;//	End time for the Tour/Hotel.

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTour_name() {
        return tour_name;
    }

    public void setTour_name(String tour_name) {
        this.tour_name = tour_name;
    }

    public String getTour_name_long() {
        return tour_name_long;
    }

    public void setTour_name_long(String tour_name_long) {
        this.tour_name_long = tour_name_long;
    }

    public String getTime_type() {
        return time_type;
    }

    public void setTime_type(String time_type) {
        this.time_type = time_type;
    }

    public String getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(String opening_hours) {
        this.opening_hours = opening_hours;
    }

    public String getStrict_start() {
        return strict_start;
    }

    public void setStrict_start(String strict_start) {
        this.strict_start = strict_start;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
