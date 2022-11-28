package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
//https://docs.viator.com/partner-api/technical/#section/Booking-concepts/Per-person-and-unit-pricing
public enum EnumUnitType {
    GROUP("Everyone"),
    ROOM("Adult"),
    PACKAGE("Child"),
    VEHICLE("Infant"),
    BIKE("Senior"),
    BOAT("Teenager"),
    AIRCRAFT("Student"),
    ;


   // "GROUP"	10847P42	Per-group pricing – the unit price is calculated according to the number of groups the specified passenger mix will fit into rather than the exact number of participants. minTravelersPerBooking and maxTravelersPerBooking must be considered as these fields relate to the available group sizes.
//"ROOM"	16621P2	Per-room pricing relates the room price, which depends on the number of participants making the booking.
//"PACKAGE"	186385P1	Per-package pricing refers to products that are sold as part of a package; for example a family package stipulating a passenger mix of two adults and two children.
  //          "VEHICLE"	10175P10	Per-vehicle pricing is calculated according to the number of vehicles required for the specified passenger mix rather than the exact number of participants. minTravelersPerBooking and maxTravelersPerBooking must be considered as these fields relate to the occupancy limitations for each vehicle. The minimum price will depend on the rate for a single vehicle.
//"BIKE"	153074P3	Per-bike pricing – identical to "per vehicle", but refers specifically to vehicles that are bikes.
 //           "BOAT"	35157P2	Per-boat pricing – identical to "per vehicle", but refers specifically to vehicles that are boats.
  //          "AIRCRAFT"	14876P5	Per-aircraft pricing – identical to "per vehicle", but refers specifically to vehicles that are aircraft.
    public static List<EnumResp> EnumList() {
        return Arrays.stream(EnumUnitType.values()).map(x->{

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

    EnumUnitType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.product.price.type.byperson.type."
                + this.name());
        return displayStatusString;
    }

}
