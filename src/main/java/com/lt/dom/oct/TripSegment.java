package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumPrivacyLevel;
import com.lt.dom.otcenum.EnumStopType;
import com.lt.dom.otcenum.EnumTripSegmentType;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//https://developer.bcdtravel.com/apis/travel-data-api/#/reference/trips/get-trip/get_trip_by_id

@Entity
public class TripSegment extends Base{
    private String title;

    private String confirmationNumber;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private EnumTripSegmentType type;

    private EnumStopType stopType = EnumStopType.Work;


    @Transient
    private Address address;

    private String segmentNumber;


    private Boolean is_deleted;//Trip was soft deleted and should not be displayed.

    private EnumPrivacyLevel privacy_level;



}
