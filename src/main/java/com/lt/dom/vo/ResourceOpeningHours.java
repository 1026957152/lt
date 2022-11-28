package com.lt.dom.vo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ResourceOpeningHours {

    private LocalDate date;
    private List<LocalTime> opening_hours;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<LocalTime> getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(List<LocalTime> opening_hours) {
        this.opening_hours = opening_hours;
    }
}
