package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumTourBookingStatus {
    // The order's current status:
    Draft(""),

    Pending(""),
    Submitted(""),
    Fulfilled(""),
    Errored(""),
    RefundInProgress(""),
    Refunded(""),
    PartialyRefunded(""),
    Cancelled(""),
    Abandonded("");



    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumTourBookingStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.tour_booking.status."
                + this.name());
        return displayStatusString;
    }

    }
