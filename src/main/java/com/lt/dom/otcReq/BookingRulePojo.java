package com.lt.dom.otcReq;

import com.lt.dom.oct.DayRule;
import com.lt.dom.otcenum.EnumAvailabilityRangetype;

import java.util.List;

public class BookingRulePojo {

    //Period From/To
    private EnumAvailabilityRangetype rangetype;
    private String from;
    private String to;

    private List<String> weeks;
    private List<String> months;

    public List<String> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<String> weeks) {
        this.weeks = weeks;
    }

    public List<String> getMonths() {
        return months;
    }

    public void setMonths(List<String> months) {
        this.months = months;
    }

    private Boolean bookable ;
    private Integer priority ;

    public EnumAvailabilityRangetype getRangetype() {
        return rangetype;
    }

    public void setRangetype(EnumAvailabilityRangetype rangetype) {
        this.rangetype = rangetype;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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
