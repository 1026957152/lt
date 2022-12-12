package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public enum EnumComponentVoucherStatus {

    NotRedeemed( "Not redeemed"),
    AlreadyRedeemed("Already redeemed"),
    PartialyRedeemed(""),



    ;
    public static List List() {
        return  Arrays.stream(EnumComponentVoucherStatus.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumComponentVoucherStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.redeaption.campaignToTourBooking.status."
                + this.name());
        return displayStatusString;
    }

}
