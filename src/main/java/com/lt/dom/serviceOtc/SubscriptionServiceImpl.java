package com.lt.dom.serviceOtc;


import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AttractionReq;
import com.lt.dom.otcReq.SubscriptionResp;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl {
    @Autowired
    private IdGenServiceImpl idGenService;


    @Autowired
    ApplicationEventPublisher eventPublisher;


    @Resource
    private SubscriptionRepository subscriptionRepository;

    @Resource
    private ComponentRepository componentRepository;

    @Resource
    private RatePlanRepository ratePlanRepository;


    @Resource
    private ComponentRightRepository componentRightRepository;

    public RatePlan createRateplan(Subscription subscription, ComponentRight component) {

        RatePlan ratePlan = new RatePlan();
        ratePlan.setBillingPeriods(EnumBillingPeriods.Month);
        ratePlan.setChargeModel(EnumChargeModel.Perunit);
        ratePlan.setListPrice(10);
        ratePlan.setListPriceBase(EnumListPriceBase.BillingPeriod);
        ratePlan.setBillCycleDay(1);
        ratePlanRepository.save(ratePlan);
        return ratePlan;
    }




    public RatePlan addComponentRight(Subscription subscription, ComponentRight component) {

        RatePlan ratePlan = new RatePlan();
        ratePlan.setBillingPeriods(EnumBillingPeriods.Month);
        ratePlan.setChargeModel(EnumChargeModel.Perunit);
        ratePlan.setListPrice(10);
        ratePlan.setListPriceBase(EnumListPriceBase.BillingPeriod);
        ratePlan.setBillingDay(EnumBillingDay.SpecificDayOfMonth);
        ratePlan.setBillCycleDay(1);

        ratePlan = ratePlanRepository.save(ratePlan);


        return ratePlan;
    }











    public Subscription create(Supplier supplier,RatePlan ratePlan,ComponentRight componentRight, SubscriptionResp pojo) {



        Subscription subscription = new Subscription();
        subscription.setSupplier(supplier.getId());
        subscription.setCode(idGenService.attractionCode());
        subscription.setName(pojo.getName());
        subscription.setCurrent_period_end(LocalDateTime.now());
        subscription.setCurrent_period_start(LocalDateTime.now());
        subscription.setTermSetting(pojo.getTermSetting());

        subscription.setRatePlan(ratePlan.getId());
        subscription.setComponentRight(componentRight.getId());


       // attraction.set(pojo.getName());

        //   attraction.setLongdesc(pojo.getLongdesc());
      //  attraction.setShortdesc(pojo.getShortdesc());
      //  attraction.setVideo(pojo.getVideo());
        subscription.setStatus(EnumSubscriptionStatus.draft);
      //  attraction.setThumbnail_image(pojo.getVideo_url());
        subscription = subscriptionRepository.save(subscription);

        return subscription;
    }


    public ComponentRight addComponent(Subscription subscription, ComponentRight component) {


        component.setSubscription(subscription.getId());
        component = componentRightRepository.save(component);

        return component;
    }


    public LocalDateTime percent_remaining(Subscription subscription, Component e) {

        return null;
    }
    public LocalDateTime percent_elapsed(Subscription subscription, Component e) {

        LocalDateTime original_billing_date = subscription.getBilling_cycle_anchor();
        LocalDateTime billing_cycle  = subscription.getBilling_cycle_anchor();
        if(e.getBillRecurringInterval().equals(EnumBillRecurringInterval.day)){
            billing_cycle  = original_billing_date.plusDays(e.getBillRecurringInterval_count());

        }
        if(e.getBillRecurringInterval().equals(EnumBillRecurringInterval.week)){
            billing_cycle  = original_billing_date.plusWeeks(e.getBillRecurringInterval_count());

        }
        if(e.getBillRecurringInterval().equals(EnumBillRecurringInterval.month)){
            billing_cycle  = original_billing_date.plusMonths(e.getBillRecurringInterval_count());
        }
        Duration duration = Duration.between(original_billing_date,billing_cycle);

        duration.toDays();
        duration.toMinutes();
        return null;
    }
        public Component create__(Subscription subscription, Component component) {


        List<Component> componentList = componentRepository.findAllBySubscription(subscription.getId());
        componentList.stream().map(e->{

            e.getBillRecurringInterval_count();
            e.getBillRecurringInterval();
            if(e.getBillRecurringInterval().equals(EnumBillRecurringInterval.day)){
                e.getBilling_cycle_anchor().plusDays(e.getBillRecurringInterval_count());

            }
            if(e.getBillRecurringInterval().equals(EnumBillRecurringInterval.week)){
                e.getBilling_cycle_anchor().plusWeeks(e.getBillRecurringInterval_count());

            }
            if(e.getBillRecurringInterval().equals(EnumBillRecurringInterval.month)){
                e.getBilling_cycle_anchor().plusMonths(e.getBillRecurringInterval_count());
            }
            return e;
        }).collect(Collectors.toList());
        component.setSubscription(subscription.getId());
        component = componentRepository.save(component);

        LocalDate firstOfNextMonth =null;// ld.with( TemporalAdjusters.firstDayOfNextMonth() ) ;
        LocalDate lastOfNextMonth = firstOfNextMonth.with( TemporalAdjusters.lastDayOfMonth() );

        return component;
    }


    public void create__(List<Subscription> subscription) {
        LocalDate Bill_Run_Day = LocalDate.now();
       // LocalDate Bill_Run_Day = LocalDate.now();
        LocalDate lastOfNextMonth = Bill_Run_Day.with( TemporalAdjusters.lastDayOfMonth() );

        if(Bill_Run_Day ==lastOfNextMonth){
            List<Subscription> subscriptionList = subscription.stream()
                    .filter(e->Bill_Run_Day.isAfter(e.getBilling_cycle_anchor().toLocalDate())).collect(Collectors.toList());

        }







    }










    @Autowired
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
        billRun.setStatus(EnumSubscriptionStatus.active);


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


/*
    private static Double calculateTransactions(LocalDate tempStart, LocalDate tempEnd, ListCollection listCollection, int week, ArrayList<OneTimeTransaction> tempResults) {
        //Weekly
        for(int i = 0; i < listCollection.getListWeekly().size(); i++) {
            foundDate(listCollection.getListWeekly().get(i), findNextDayOfWeekOccurance(tempStart, listCollection.getListWeekly().get(i).getDayOfWeek(), 1), tempResults);
        }
        //Monthly
        for(int i = 0; i < listCollection.getListMonthly().size(); i++) {
            monthlyCalculator(listCollection.getListMonthly().get(i), tempStart, tempEnd, listCollection.getListMonthly().get(i).getRecurringDate(), tempResults);
        }
        //One Time
        for(int i = 0; i < listCollection.getListOneTime().size(); i++) {
            dateComparison(listCollection.getListOneTime().get(i), listCollection.getListOneTime().get(i).getEndDate(), tempStart, tempEnd, tempResults);
        }
        //Limited Monthly
        for(int i = 0; i < listCollection.getListLimited().size(); i++) {
            if(listCollection.getListLimited().get(i).getEndDate().isAfter(tempStart.minusDays(1))) {
                monthlyCalculator(listCollection.getListLimited().get(i), tempStart, tempEnd, listCollection.getListLimited().get(i).getRecurringDate(), tempResults);
            }
        }

        return sumTransactions(tempResults);
    }



    private static Double sumTransactions(ArrayList<OneTimeTransaction> tempResults) {
        Double sum = 0.0;
        for(int i = 0; i < tempResults.size(); i++) {
            sum += tempResults.get(i).getAmount();
        }
        return sum;
    }*/
}
