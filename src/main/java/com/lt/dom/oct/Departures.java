package com.lt.dom.oct;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Departures {
    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    private String Type; ;//Special Closure
    //Period From/To
    private LocalDate PeriodFrom;
    private LocalDate PeriodTo;

    private boolean monday;//	See Day Rules	Rules specific to bookings on a Monday
    private boolean tuesday;//	See Day Rules	Rules specific to bookings on a Tuesday
    private boolean wednesday;//	See Day Rules	Rules specific to bookings on a Wednesday
    private boolean thursday;//	See Day Rules	Rules specific to bookings on a Thursday
    private boolean friday;//	See Day Rules	Rules specific to bookings on a Friday
    private boolean saturday;//	See Day Rules	Rules specific to bookings on a Saturday
    private boolean sunday;//	See Day Rules	Rules specific to bookings on a Sunday

    private List<String> times;

    public LocalDate getPeriodFrom() {
        return PeriodFrom;
    }

    public void setPeriodFrom(LocalDate periodFrom) {
        PeriodFrom = periodFrom;
    }

    public LocalDate getPeriodTo() {
        return PeriodTo;
    }

    public void setPeriodTo(LocalDate periodTo) {
        PeriodTo = periodTo;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }
}
