package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public enum EnumPhysicalCardBlockReasons {
    lost("physical"),
    stolen("virtual"),
    employee_left("virtual"),
    damaged("virtual"),


/*    lost	The card has been lost
    stolen	The card has been stolen
    employee_left	The card holder left the company
    damaged	The card is damaged*/
    ;

    public static List<EnumResp> from() {
        return Arrays.asList(EnumPhysicalCardBlockReasons.values()).stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumPhysicalCardBlockReasons(String name) {

        this.name = name;
    }
/*
    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.pass.type."
                + this.name());
        return displayStatusString;
    }*/

}
