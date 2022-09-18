package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumDeviceOnline {
    online("registered"),
    offline("suspended"),



    ;



    ;
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumDeviceOnline(String name) {

        this.name = name;
    }
    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.device.live_info.online."
                + this.name());
        return displayStatusString;
    }



}
