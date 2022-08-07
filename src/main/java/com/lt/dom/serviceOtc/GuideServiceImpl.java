package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.GuideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuideServiceImpl {


    @Autowired
    private GuideRepository guideRepository;



    public Guide beGuide(User user) {


        Guide guide = new Guide();

        guide.setUserId(user.getId());

        return guideRepository.save(guide);

    }
}
