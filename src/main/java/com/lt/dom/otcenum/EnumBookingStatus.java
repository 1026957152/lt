package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public enum EnumBookingStatus {
    // The order's current status:
    Draft(""),

    Pending(""),

    Payment_Pending(""),

   // Submitted_Confirmed(""),  //CONFIRMED
    PAID(""), //  已支付
    Free(""), //  已支付

    EXPIRED(""), //支付超时


    Fulfilled(""), // 已履行 合约



    Errored(""),
    RefundInProgress(""),
    Refunded(""),
    PartialyRefunded(""),
    Cancelled(""),
    Abandonded("");



    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;
    public static List<EnumResp> from() {
        return Arrays.stream(EnumBookingStatus.values()).map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());
    }
    @Autowired
    private MessageSource messageSource;

    EnumBookingStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.tour_booking.status."
                + this.name());
        return displayStatusString;
    }

    }
