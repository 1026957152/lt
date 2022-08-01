package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.repository.AttractionRepository;
import com.lt.dom.repository.BookingRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TouristAttractionServiceImpl {


    @Autowired
    private AttractionRepository attractionRepository;


    public Attraction addAttraction(TouristAttraction touristAttraction) {
        Attraction attraction = new Attraction();

        return attractionRepository.save(attraction);
    }
}
