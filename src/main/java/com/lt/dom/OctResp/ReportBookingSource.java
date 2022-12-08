package com.lt.dom.OctResp;


import com.lt.dom.oct.Reservation;
import com.lt.dom.otcenum.EnumPlatform;

import java.util.List;
import java.util.Map;

public class ReportBookingSource {

    private String RevenueToday;
    private String RevenueMTD;


    private String Bookings_Total_today;
    private String Bookings_Already_arrived;
    private String PAX_Total_today;
    private String PAX_Already_arrived;
    private List groupby;
    private List groupby_today;


    public String getRevenueToday() {
        return RevenueToday;
    }

    public void setRevenueToday(String revenueToday) {
        RevenueToday = revenueToday;
    }

    public String getRevenueMTD() {
        return RevenueMTD;
    }

    public void setRevenueMTD(String revenueMTD) {
        RevenueMTD = revenueMTD;
    }

    public String getBookings_Total_today() {
        return Bookings_Total_today;
    }

    public void setBookings_Total_today(String bookings_Total_today) {
        Bookings_Total_today = bookings_Total_today;
    }

    public String getBookings_Already_arrived() {
        return Bookings_Already_arrived;
    }

    public void setBookings_Already_arrived(String bookings_Already_arrived) {
        Bookings_Already_arrived = bookings_Already_arrived;
    }

    public void setPAX_Total_today(String pax_total_today) {
        this.PAX_Total_today = pax_total_today;
    }

    public String getPAX_Total_today() {
        return PAX_Total_today;
    }

    public void setPAX_Already_arrived(String pax_already_arrived) {
        this.PAX_Already_arrived = pax_already_arrived;
    }

    public String getPAX_Already_arrived() {
        return PAX_Already_arrived;
    }

    public void setGroupby(List groupby) {
        this.groupby = groupby;
    }

    public List getGroupby() {
        return groupby;
    }

    public void setGroupby_today(List groupby_today) {
        this.groupby_today = groupby_today;
    }

    public List getGroupby_today() {
        return groupby_today;
    }
}
