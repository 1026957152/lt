package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.repository.ComponentRightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartnerServiceImpl {


    @Autowired
    private ComponentRightRepository componentRightRepository;



    public List<ComponentRight> listComponentRights(Partner partner) {



        ComponentRight probe = new ComponentRight();
        probe.setSupplierId(partner.getSupplierId());

        Example<ComponentRight> example = Example.of(probe);

        return componentRightRepository.findAll(example);
    }
}
