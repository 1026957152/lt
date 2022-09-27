package com.lt.dom.serviceOtc;


import com.lt.dom.oct.Attraction;
import com.lt.dom.oct.Component;
import com.lt.dom.oct.Subscription;
import com.lt.dom.oct.Supplier;
import com.lt.dom.otcReq.AttractionReq;
import com.lt.dom.otcenum.EnumBillRecurringInterval;
import com.lt.dom.otcenum.EnumProductStatus;
import com.lt.dom.otcenum.EnumSubscriptionStatus;
import com.lt.dom.repository.AttractionRepository;
import com.lt.dom.repository.ComponentRepository;
import com.lt.dom.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
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


    public Subscription create(Supplier supplier, AttractionReq pojo) {

        Subscription attraction = new Subscription();
        attraction.setSupplier(supplier.getId());
        attraction.setCode(idGenService.attractionCode());
        attraction.setName(pojo.getName());
        attraction.setCurrent_period_end(LocalDateTime.now());
        attraction.setCurrent_period_start(LocalDateTime.now());
       // attraction.set(pojo.getName());

        //   attraction.setLongdesc(pojo.getLongdesc());
      //  attraction.setShortdesc(pojo.getShortdesc());
      //  attraction.setVideo(pojo.getVideo());
        attraction.setStatus(EnumSubscriptionStatus.draft);
      //  attraction.setThumbnail_image(pojo.getVideo_url());
        attraction = subscriptionRepository.save(attraction);

        return attraction;
    }


    public Component create(Subscription subscription, Component component) {


        component.setSubscription(subscription.getId());
        component = componentRepository.save(component);

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
