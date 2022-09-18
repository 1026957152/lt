package com.lt.dom.vo;

import cn.hutool.core.date.Week;
import com.lt.dom.otcenum.EnumAvailabilityRangetype;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

public class BookingRuleVO {

    //Period From/To

    @NotNull
    private EnumAvailabilityRangetype rangetype;

    @NotNull
    private Boolean bookable ;
    @NotNull
    private Integer priority ;


    private LocalDate bookings_from;//	string the earliest time bookings are allowed, in 24 hour format eg 09:00
    private LocalDate bookings_to;//	string the latest time bookings are allowed, in 24 hour format eg 23:00

    private LocalTime time_from;//	string the earliest time bookings are allowed, in 24 hour format eg 09:00
    private LocalTime time_to;//	string the latest time bookings are allowed, in 24 hour format eg 23:00

    private DayOfWeek week_from;//	string the earliest time bookings are allowed, in 24 hour format eg 09:00
    private DayOfWeek week_to;//	string the latest time bookings are allowed, in 24 hour format eg 23:00

    private List<DayOfWeek> weeks;//	string the latest time bookings are allowed, in 24 hour format eg 23:00

    public List<DayOfWeek> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<DayOfWeek> weeks) {
        this.weeks = weeks;
    }

    private Month month_from;//	string the earliest time bookings are allowed, in 24 hour format eg 09:00
    private Month month_to;//	string the latest time bookings are allowed, in 24 hour format eg 23:00
    private List<Month> months;//	string the latest time bookings are allowed, in 24 hour format eg 23:00

    public List<Month> getMonths() {
        return months;
    }

    public void setMonths(List<Month> months) {
        this.months = months;
    }

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

    public DayOfWeek getWeek_from() {
        return week_from;
    }

    public void setWeek_from(DayOfWeek week_from) {
        this.week_from = week_from;
    }

    public DayOfWeek getWeek_to() {
        return week_to;
    }

    public void setWeek_to(DayOfWeek week_to) {
        this.week_to = week_to;
    }

    public EnumAvailabilityRangetype getRangetype() {
        return rangetype;
    }

    public void setRangetype(EnumAvailabilityRangetype rangetype) {
        this.rangetype = rangetype;
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
}
