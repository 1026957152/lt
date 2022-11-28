package com.lt.dom.otcReq;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

//https://www3.septa.org/#/Real%20Time%20Data/trainView



//https://smart-ryde.com/
public class CarHireReq {


    private String pickupLocation;
    private String dropoffLocation;


    private LocalDate inputDate;
    private LocalTime inputTime;
    private Integer selectAdult;
    private Integer selectChild;

    private Boolean checkReturnJourney;


    private LocalDate inputDateReturn;
    private LocalTime inputTimeReturn;


}
