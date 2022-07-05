package com.lt.dom.oct;

public class PickupPoint {

    private String pickup_name;//	The name of the pickup point. This is a mandatory field.
    private String description;//	Any extra information for the client, e.g. "Meet in the reception lounge"
    private String address1;//	First line of the address
    private String address2;//	Second line of the address
    private String postcode;//	Postcode
    private String lat;//	Latitude - geographic coordinate
    private String long_;//	Longitude - geographic coordinate
    private String staff_only;//	1/0
      //      0 - All - Can be added by customers, travel agents and staff (Default)
//1 - Staff only - Can ONLY be added to a booking by staff only (not customers / travel agents)
    private String rank	;//Numeric - Can be used as an additional ordering value for hotel pickup reports
    private String  viator_name;//	If you configure the hotel name EXACTLY as configured in Viator, TourCMS will map Viator bookings to the correct pickup point
}
