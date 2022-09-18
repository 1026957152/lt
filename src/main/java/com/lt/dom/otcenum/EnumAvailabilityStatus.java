package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumAvailabilityStatus {
    AVAILABLE("Campaigns","Read Campaigns."),
    SOLD_OUT("Vouchers","Read Vouchers."),
    LIMITED("Distributions","Read and export Distributions and Publications."),
    CLOSED("Products","Read Products");


//    The status of that date. Possible values are:
  //  AVAILABLE There are availabilities available on this date for sale.
  //  SOLD_OUT This date was available but is now fully sold out.
  //  LIMITED This date is available but has less than 50% capacity left.
  //  CLOSED This date is closed and not available for sale.





    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;
    public String type;
    @Autowired
    private MessageSource messageSource;

    EnumAvailabilityStatus(String ob, String name) {

        this.type = ob;
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.role.action.type."
                + this.name());
        return displayStatusString;
    }




}
