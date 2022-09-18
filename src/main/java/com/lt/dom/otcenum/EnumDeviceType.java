package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public enum EnumDeviceType {
/*    CAMERA("barcode"),
    DISHWASHER("reference number"),
    DRYER("Barcode scan"),
    LIGHT("Facial Recognition"),*/
    OUTLET("NFC tap"),
    HANDSET("handset"),
    FICIAL_RECOGNITION("facial recognition"),
    QRCODE_READER("qrcode reader"),

/*

    PHONE("barcode"),
    REFRIGERATOR("reference number"),
    SCENE("Barcode scan"),
    SOUNDBAR("Facial Recognition"),
    SPEAKER("NFC tap"),

    SWITCH("barcode"),
    THERMOSTAT("reference number"),
    TV("Barcode scan"),
    VACUUM("Facial Recognition"),
    WASHER("NFC tap"),*/

    ;

    public static List<EnumResp> from() {

        return Arrays.stream(EnumDeviceType.values()).map(x->{
            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }

    public static List<EnumResp> from(List<EnumDeviceType> name) {

        return name.stream().map(x->{
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

    EnumDeviceType(String name) {

        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.device.type."
                + this.name());
        return displayStatusString;
    }

}
