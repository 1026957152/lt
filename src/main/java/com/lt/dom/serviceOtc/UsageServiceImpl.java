package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BillRunReq;
import com.lt.dom.otcenum.EnumBillRecurringInterval;
import com.lt.dom.otcenum.EnumBillRunStatus;
import com.lt.dom.otcenum.EnumSubscriptionStatus;
import com.lt.dom.otcenum.EnumTypesOfBillRuns;
import com.lt.dom.repository.*;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UsageServiceImpl {

    @Autowired
    ComponentRepository componentRepository;
    @Autowired
    BillRunRepository billRunRepository;
    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    BillRunProcessingSubscriptionRepository billRunProcessingSubscriptionRepository;


    @Autowired
    RatePlanRepository ratePlanRepository;

    @Autowired
    UsageRepository usageRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    RedemptionEntryRepository redemptionEntryRepository;


    public BillRun create(Supplier supplier, BillRunReq billRunReq) {






        BillRun billRun = new BillRun();
        billRun.setSupplier(supplier.getId());
        billRun.setStatus(EnumBillRunStatus.Pending);
        billRun.setType(billRunReq.getType());
        billRun.setTargetDate(billRunReq.getTargetDate());
        billRun.setRepeats(billRunReq.getRepeats());



        List<Subscription> subscriptionList = subscriptionRepository.findAllById(billRunReq.getSubscriptions());


        BillRun finalBillRun = billRun;
        billRun.setProcessingSubscriptions(subscriptionList.stream().map(e->{
            BillRunProcessingSubscription billRunProcessingSubscription = new BillRunProcessingSubscription();
            billRunProcessingSubscription.setBillRun(finalBillRun);
            billRunProcessingSubscription.setSubscription(e.getId());
            return billRunProcessingSubscription;

        }).collect(Collectors.toList()));

        billRun = billRunRepository.save(billRun);

        if(billRun.getType().equals(EnumTypesOfBillRuns.ad_hoc)){
            processing(billRun);
        }


        return billRun;
    }

    public List<Integer> getFees() {

        return Arrays.asList(1);

    }

    public void billUpdate(List<Triplet<ComponentVounch, Component, Integer>> componentVounchList) {

        List<RedemptionEntry> redemptionEntries = redemptionEntryRepository.findAll();


        RedemptionEntry entry = new RedemptionEntry();




        Usage usage = new Usage();
        usage.setQty(1l);

        usageRepository.save(usage);

    }

    public void processing(BillRun billRun) {

        List<Subscription> subscriptionList = subscriptionRepository.findAllById(billRun.getProcessingSubscriptions().stream().map(e->e.getSubscription()).collect(Collectors.toList()));


        billRun.getTargetDate();

        List<Usage> usageList = usageRepository.findAll();
        Subscription subscription = subscriptionList.get(0);



        LocalDate startDate = subscription.getCurrent_period_start().toLocalDate();
        LocalDate endDate = subscription.getCurrent_period_end().toLocalDate();


        List<Usage> usages = usageList.stream()
                .filter(e->e.getEndDate().isAfter(subscription.getBilling_cycle_anchor().toLocalDate()))
                .collect(Collectors.toList());


        Optional<RatePlan> optionalRatePlan = ratePlanRepository.findById(subscription.getRatePlan());
        RatePlan ratePlan = optionalRatePlan.get();

        Integer total = usages.stream().mapToInt(e->{

            return ratePlan.getListPrice()*e.getQty().intValue();

        }).sum();


        Invoice invoice = new Invoice();


        invoiceRepository.save(invoice);

    }



    @Scheduled(fixedDelay = 500000)
    // @Scheduled(cron = "${erwin.cron:0/2 * * * * ?}")
    public void cronTaskYmlDemo() {

    }




    //  @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    public void start(BillRun billRun) {
        if(billRun.getStatus().equals(EnumSubscriptionStatus.active)){
            return;
        }
        billRun.setRepeats(EnumBillRecurringInterval.day);

        PeriodicTrigger periodicTrigger
                = new PeriodicTrigger(15, TimeUnit.DAYS);
        periodicTrigger.setInitialDelay(Date.from(LocalDateTime.now().plusMinutes(1)
                .atZone(ZoneId.systemDefault()).toInstant()).getTime());
        taskScheduler.schedule(
                new RunnableTask("Periodic Trigger"), periodicTrigger);

        RunnableTask runnableTask = new RunnableTask("Periodic Trigger") ;
        billRun.setStatus(EnumBillRunStatus.Pending);


    }


    class RunnableTask implements Runnable {

        private String message;

        public RunnableTask(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            System.out.println("Runnable Task with " + message + " on thread " + Thread.currentThread().getName());
        }
    }

}
