package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.ZoneRowReq;
import com.lt.dom.OctResp.ZoneRowResp;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.SeatPojo;
import com.lt.dom.otcReq.SeatingLayoutPojo;
import com.lt.dom.otcReq.ZoneReq;
import com.lt.dom.otcReq.ZoneResp;
import com.lt.dom.repository.SeatRepository;
import com.lt.dom.repository.SeatingLayoutRepository;
import com.lt.dom.repository.ZoneRepository;
import com.lt.dom.repository.ZoneRowRepository;
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
    private ZoneRowRepository zoneRowRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ZoneRepository zoneRepository;

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
    public SeatingLayout updateSeatingLayout(SeatingLayout seatingLayout, SeatingLayoutPojo pojo) {

        seatingLayout.setColumns_(pojo.getColumns());
        seatingLayout.setRows_(pojo.getRows());
        seatingLayout.setTheatre(pojo.getTheatre());

        seatingLayout = seatingLayoutRepository.save(seatingLayout);
        return seatingLayout;
    }
    public SeatingLayout createSeatingLayout(Supplier theatre, SeatingLayoutPojo pojo) {

        SeatingLayout seatingLayout = new SeatingLayout();
        seatingLayout.setColumns_(pojo.getColumns());
        seatingLayout.setRows_(pojo.getRows());
        seatingLayout.setSupplier(theatre.getId());
        seatingLayout.setTheatre(pojo.getTheatre());

        seatingLayout = seatingLayoutRepository.save(seatingLayout);
        return seatingLayout;
    }

    public SeatingLayout createSeatingLayout(Theatre theatre, SeatingLayoutPojo pojo) {

        SeatingLayout seatingLayout = new SeatingLayout();
        seatingLayout.setColumns_(pojo.getColumns());
        seatingLayout.setRows_(pojo.getRows());

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
                seat.setSeatingLayoutId(seatingLayout.getId());
                seat.setType(x.getType());
                seat.setColumn_(x.getColumn());
                seat.setSeatTier(x.getSeatTier());
                seat.setRow_(x.getRow());
                return seat;
            }
        }).collect(Collectors.toList());
        return seatRepository.saveAll(processSeat).stream().map(x->{
            SeatPojo seatPojo = new SeatPojo();
            return seatPojo;
        }).collect(Collectors.toList());


    }


    public List<ZoneResp> addZoneForSeatingLayout(SeatingLayout seatingLayout, List<ZoneReq> pojos) {



        List<Zone> list = zoneRepository.findBySeatingLayoutId(seatingLayout.getId());


        List<Zone> processSeat = pojos.stream().map(x-> {

            Zone seat = new Zone();
                seat.setName(x.getName());
                seat.setSeatingLayoutId(seatingLayout.getId());
                return seat;
        }).collect(Collectors.toList());
        return zoneRepository.saveAll(processSeat).stream().map(x->{
            ZoneResp seatPojo = ZoneResp.from(x);

            return seatPojo;
        }).collect(Collectors.toList());


    }

    public List<ZoneRowResp> addSeatForZone(Zone zone, List<ZoneRowReq> pojos) {


        List<ZoneRow> list = zoneRowRepository.findAllByZone(zone.getId());


        List<ZoneRow> processSeat = pojos.stream().map(x-> {
            Optional<ZoneRow> seatOptional = list.stream().filter(y -> (y.getName() == x.getName())).findAny();
            if(seatOptional.isPresent()){
                ZoneRow seat = seatOptional.get();

                seat.setName(x.getName());
                return seat;
            }else{
                ZoneRow seat = new ZoneRow();
                seat.setName(x.getName());
                return seat;
            }
        }).collect(Collectors.toList());
        return zoneRowRepository.saveAll(processSeat).stream().map(x->{
            ZoneRowResp seatPojo = new ZoneRowResp();
            return seatPojo;
        }).collect(Collectors.toList());


    }
}
