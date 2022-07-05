package com.lt.dom.otcReq;

import com.lt.dom.oct.DayRule;

import java.util.List;

public class BookingRuleDeparturePojo {

    //Period From/To
    private String PeriodFrom;
    private String PeriodTo;

    private DayRule monday;//	See Day Rules	Rules specific to bookings on a Monday
    private DayRule tuesday;//	See Day Rules	Rules specific to bookings on a Tuesday
    private DayRule wednesday;//	See Day Rules	Rules specific to bookings on a Wednesday
    private DayRule thursday;//	See Day Rules	Rules specific to bookings on a Thursday
    private DayRule friday;//	See Day Rules	Rules specific to bookings on a Friday
    private DayRule saturday;//	See Day Rules	Rules specific to bookings on a Saturday
    private DayRule sunday;//	See Day Rules	Rules specific to bookings on a Sunday

    private List<String> times;



    private String bookings_from;//	string the earliest time bookings are allowed, in 24 hour format eg 09:00
    private String bookings_to;//	string the latest time bookings are allowed, in 24 hour format eg 23:00
    private boolean booking_available;//	boolean Whether or not a booking would be allowed, based on the Can Book checkbox in Collins
    private int max_duration;//	integer The maximum duration for a booking in minutes *
    private String booking_notes;//	string The ‘Bookings Policy’ message
    private int min_people;//	number The minumum number of guests allowed *
    private int max_people;//	number The maximum number of guests allowed *
    private String bookings_shut;//	string The time when same day bookings close *

}
