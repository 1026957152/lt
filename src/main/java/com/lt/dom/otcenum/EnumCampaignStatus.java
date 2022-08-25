package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumCampaignStatus {
    Draft("Draft"),

    paused("paused"),
    schedule("schedule"),
  //  sending("sending"),
  //  sent("sent"),

    canceled("canceled"),
  //  canceling("canceling"),
    archived("archived"),





    ;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumCampaignStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.campaign.status."
                + this.name());
        return displayStatusString;
    }
}
