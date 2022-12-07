package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public enum EnumFormFactorType {
    physical("physical"),
    virtual("virtual"),

    one_time_virtual_card("virtual"),
    capped_virtual_card("virtual"),
    recurring_virtual_card("virtual"),

/*
    card	physical	Physical card.
    virtual_card	virtual	Classic virtual card.
    one_time_virtual_card	virtual	Virtual card for a single payment, blocked after the payment.
    capped_virtual_card	virtual	Virtual card for multiple payments, blocked once the ceiling is reached.
    recurring_virtual_card	virtual	Virtual card with recurring balance.
*/

    ;

    public static List<EnumResp> from() {
        return Arrays.asList(EnumFormFactorType.values()).stream().map(x->{
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

    EnumFormFactorType(String name) {

        this.name = name;
    }

/*
    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.pass.type."
                + this.name());
        return displayStatusString;
    }
*/

}
