package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ClainQuotaPojo;
import com.lt.dom.otcReq.EquipmentPojo;
import com.lt.dom.repository.BookingRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentServiceImpl {


    @Autowired
    private BookingRuleRepository bookingRuleRepository;



    public Equipment create(Supplier supplier, EquipmentPojo pojo) {
        return null;
    }
}
