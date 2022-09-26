package com.lt.dom.serviceOtc;


import com.lt.dom.credit.RequestCredit;
import com.lt.dom.oct.ApplicationFee;
import com.lt.dom.oct.Component;
import com.lt.dom.oct.ComponentVounch;
import com.lt.dom.otcenum.EnumBillRecurringInterval;
import com.lt.dom.repository.ApplicationFeeRepository;
import com.lt.dom.repository.ComponentRepository;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl {

    @Autowired
    ComponentRepository componentRepository;

    public List<Integer> getFees() {

        return Arrays.asList(1);

    }

    public void billUpdate(List<Triplet<ComponentVounch, Component, Integer>> componentVounchList) {

        List<Component> componentList = componentVounchList.stream().map(e->{
            Component component = e.getValue1();
            component.setBilling_unit_amount(component.getBilling_unit_amount()+e.getValue2());
            return component;
        }).collect(Collectors.toList());
        componentRepository.saveAll(componentList);

    }



    @Scheduled(fixedDelay = 500000)
    // @Scheduled(cron = "${erwin.cron:0/2 * * * * ?}")
    public void cronTaskYmlDemo() {

        List<Component> componentList = componentRepository.findAllByBillRecurringInterval(EnumBillRecurringInterval.day);




    }

}
