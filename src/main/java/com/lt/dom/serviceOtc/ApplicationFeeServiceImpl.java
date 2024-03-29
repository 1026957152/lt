package com.lt.dom.serviceOtc;


import com.lt.dom.oct.ApplicationFee;
import com.lt.dom.oct.BookingRule;
import com.lt.dom.oct.Departures;
import com.lt.dom.oct.Product;
import com.lt.dom.repository.ApplicationFeeRepository;
import com.lt.dom.repository.BookingRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationFeeServiceImpl {

    @Autowired
    ApplicationFeeRepository applicationFeeRepository;

    public List<Integer> getFees() {



        return Arrays.asList(1);

    }

    public List<Integer> createFee() {



        ApplicationFee applicationFee = new ApplicationFee();
        applicationFee.setRefunded(true);
     //   applicationFee.setAmount_refunded(x.getAmount());
       // applicationFee.setRefund(refund.getId());
        applicationFeeRepository.save(applicationFee);



        return Arrays.asList(1);

    }

}
