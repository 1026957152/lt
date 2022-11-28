package com.lt.dom.Scheduled;


import com.lt.dom.credit.*;
import com.lt.dom.oct.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import org.apache.tomcat.jni.Local;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Component
public class RedemptionScheduled {
    private static final Logger logger = LoggerFactory.getLogger(RedemptionScheduled.class);


    @Autowired
    UsageRepository usageRepository;
    @Autowired
    RightRedemptionEntryRepository rightRedemptionEntryRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;


    @Autowired
    IdGenServiceImpl idGenService;

  //  @Scheduled(fixedDelay = 500000)
   // @Scheduled(cron = "${erwin.cron:0/2 * * * * ?}")
    public void cronTaskYmlDemo() {


        List<RightRedemptionEntry> redemptionEntryList = rightRedemptionEntryRepository.findAll();



        List<Subscription> subscriptions = subscriptionRepository.findAll();
        List<Long> longList = subscriptions.stream().map(e->e.getComponentRightId()).collect(toList());




        List<Pair<Subscription,RightRedemptionEntry>> rightRedemptionEntryList = redemptionEntryList.stream()
                .filter(e->longList.contains(e.getComponent_right()))
                .map(e->{

                 //   longList.get(e.getComponent_right());
                return Pair.with(new Subscription(),e);
        }).collect(toList());


        Map<Subscription,Long> aa = rightRedemptionEntryList.stream()
                .collect(Collectors.groupingBy(e->e.getValue0(),Collectors.counting()));


        usageRepository.saveAll(aa.entrySet().stream().map(e->{
            Usage usage = new Usage();
            usage.setQty(e.getValue().longValue());
            usage.setStartDate(LocalDate.now());
            usage.setEndDate(LocalDate.now());
            usage.setSubscription(e.getKey().getId());
            return usage;
        }).collect(Collectors.toList()));

        System.out.println("cron yml demo");
    }
}
