package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public enum EnumDaytourDays {
    one(1),
    two(2),
    three(3),
    four(4),
    five(5),


    ;


    public static List<EnumResp> from() {
       return Arrays.asList(EnumDaytourDays.values()).stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public int value;

    @Autowired
    private MessageSource messageSource;

    EnumDaytourDays(int name) {
        this.value = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.product.daytour.days."
                + this.name());
        return displayStatusString;
    }
}
