package com.lt.dom.serviceOtc;


import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Product;
import com.lt.dom.repository.BookingRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailabilityServiceImpl {


    @Autowired
    private BookingRuleRepository bookingRuleRepository;


    public static List<LocalDate> getDatesBetweenUsingJava9(
            LocalDate startDate, LocalDate endDate) {

        return startDate.datesUntil(endDate)
                .collect(Collectors.toList());
    }

    public List<Calendar> listAvailability(Product product) {

        List<BookingRule> bookingRules = bookingRuleRepository.findByProductId(product.getId());
        BookingRule bookingRule = bookingRules.get(0);
        List<LocalDate> localDates = getDatesBetweenUsingJava9(bookingRule.getBookings_from(),bookingRule.getBookings_to());


        return null;
    }
}
