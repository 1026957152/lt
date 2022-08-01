package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcReq.SeatPojo;
import com.lt.dom.otcReq.SeatingLayoutPojo;
import com.lt.dom.repository.SeatRepository;
import com.lt.dom.repository.SeatingLayoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeatingLayoutServiceImpl {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatingLayoutRepository seatingLayoutRepository;

    public static List<LocalDate> getAvailability(List<Departures> departures) {


        List<LocalDate> localDates = departures.stream().filter(x->x.getType() == "" ).map(x->{

            return getDatesBetweenUsingJava9(x);

        }).flatMap(List::stream).collect(Collectors.toList());

        List<LocalDate> close = departures.stream().filter(x->x.getType() == "" ).map(x->{

            return getDatesBetweenUsingJava9(x);

        }).flatMap(List::stream).collect(Collectors.toList());


        return localDates.stream().filter(close::contains).collect(Collectors.toList());


    }


    public static List<LocalDate> getDatesBetweenUsingJava9(Departures departures) {

        List<LocalDate> localDates =  departures.getPeriodFrom().datesUntil(departures.getPeriodTo())
                .collect(Collectors.toList());


        return localDates.stream().filter(x->{
            if(departures.isMonday()){
                return x.getDayOfWeek() == DayOfWeek.MONDAY;
            }
            if(departures.isThursday()){
                return x.getDayOfWeek() == DayOfWeek.THURSDAY;
            }
            if(departures.isWednesday()){
                return x.getDayOfWeek() == DayOfWeek.WEDNESDAY;
            }
            if(departures.isWednesday()){
                return x.getDayOfWeek() == DayOfWeek.WEDNESDAY;
            }
            return false;
        }).collect(Collectors.toList());
    }

    public List<Calendar> listAvailability(Product product) {

        List<BookingRule> bookingRules =null;// bookingRuleRepository.findByProductId(product.getId());
        BookingRule bookingRule = bookingRules.get(0);
        List<LocalDate> localDates =null;// getDatesBetweenUsingJava9(bookingRule.getBookings_from(),bookingRule.getBookings_to());


        return null;
    }

    public SeatingLayout get(Theatre theatre) {

        return new SeatingLayout();

    }

    public SeatingLayout createSeatingLayout(Theatre theatre, SeatingLayoutPojo pojo) {

        SeatingLayout seatingLayout = new SeatingLayout();
        seatingLayout.setColumns(pojo.getColumns());
        seatingLayout.setRows(pojo.getRows());

        seatingLayout = seatingLayoutRepository.save(seatingLayout);
        return seatingLayout;
    }

    public List<SeatPojo> addSeatForSeatingLayout(SeatingLayout seatingLayout, List<SeatPojo> pojos) {

        List<Seat> list = seatRepository.findBySeatingLayoutId(seatingLayout.getId());


        List<Seat> processSeat = pojos.stream().map(x-> {
            Optional<Seat> seatOptional = list.stream().filter(y -> (y.getColumn_() == x.getColumn()) && (y.getRow_() == x.getRow())).findAny();
            if(seatOptional.isPresent()){
                Seat seat = seatOptional.get();

                seat.setType(x.getType());
                return seat;
            }else{
                Seat seat = new Seat();
                seat.setSeatName(x.getSeatName());
                seat.setType(x.getType());
                seat.setColumn_(x.getColumn());
                seat.setRow_(x.getRow());
                return seat;
            }
        }).collect(Collectors.toList());
        return seatRepository.saveAll(processSeat).stream().map(x->{
            SeatPojo seatPojo = new SeatPojo();
            return seatPojo;
        }).collect(Collectors.toList());


    }
}
