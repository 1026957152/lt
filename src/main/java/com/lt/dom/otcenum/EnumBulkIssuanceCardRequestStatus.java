package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public enum EnumBulkIssuanceCardRequestStatus {
    SUBMITTED("SUBMITTED"),
    DRAFT("DRAFT"),

/*    active
    The card can approve authorizations.

            inactive
    The card will decline authorizations with the card_inactive reason.

            canceled
    The card will decline authorization, and no authorization object will be recorded. This status is permanent.
   */

    ;
    public String name;

    EnumBulkIssuanceCardRequestStatus(String name) {

        this.name = name;
    }

    public static List<EnumResp> from() {
        return Arrays.asList(EnumBulkIssuanceCardRequestStatus.values()).stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());


    @Autowired
    private MessageSource messageSource;


    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.pass.bulkissuance.status."
                + this.name());
        return displayStatusString;
    }

}
