package com.lt.dom.oct;

import java.time.LocalDateTime;
import java.util.List;

public class Departures {

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

}
