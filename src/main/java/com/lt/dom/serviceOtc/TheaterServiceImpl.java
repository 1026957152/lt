package com.lt.dom.serviceOtc;
//http://learnmongodbthehardway.com/schema/theater/

import com.lt.dom.oct.Theatre;
import com.lt.dom.oct.TheaterSession;
import com.lt.dom.repository.BookingRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheaterServiceImpl {


    @Autowired
    private BookingRuleRepository bookingRuleRepository;


    public static List<LocalDate> getDatesBetweenUsingJava9(
            LocalDate startDate, LocalDate endDate) {

        return startDate.datesUntil(endDate)
                .collect(Collectors.toList());
    }

    public List<Calendar> CreateSession(Theatre theatre) {

        TheaterSession theaterSession = new TheaterSession();
        theaterSession.setTheaterId(theatre.getId());
        theaterSession.setSeatsAvailable(theatre.getSeatsAvailable());
     //   theaterSession.setSeats(theatre.getSeats());

/*
        theaterSession.setName();
        theaterSession.setDescription();
        theaterSession.setStart();
        theaterSession.setEnd();
*/




        return null;
    }


    public List<Calendar> ReservingSeats(TheaterSession session , List<int[]> seats) {


        for(int[] seet: seats){
            session.getSeats()[seet[0]][seet[1]] = 0;

        }




        return null;
    }
}
