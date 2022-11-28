package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.RatePlanReq;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.ComponentRightPojo;
import com.lt.dom.otcReq.SubscriptionReq;
import com.lt.dom.OctResp.SubscriptionResp;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.DayOfWeek;
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

    @Autowired
    ComponentRightServiceImpl componentRightService;

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
        ratePlan.setBillingPeriods(EnumBillingPeriod.Month);
        ratePlan.setChargeModel(EnumChargeModel.Perunit);
        ratePlan.setListPrice(10);
        ratePlan.setListPriceBase(EnumListPriceBase.BillingPeriod);
        ratePlan.setBillCycleDay(1);
        ratePlanRepository.save(ratePlan);
        return ratePlan;
    }


    public RatePlan createRateplan(Product product, RatePlanReq ratePlanReq, List<Partner> partner) {

        RatePlan ratePlan = new RatePlan();
        ratePlan.setCode(idGenService.ratePlanCode());

        ratePlan.setPrivacyLevel(EnumPrivacyLevel.shareable);



        ratePlan.setSharePartners(partner.stream().map(e->{

            PartnerShareRatePlan partnerShareRatePlan = new PartnerShareRatePlan();
            partnerShareRatePlan.setRatePlan(ratePlan);
            partnerShareRatePlan.setPartnerSupplier(e.getPartner());
            partnerShareRatePlan.setPartner(e.getId());
            partnerShareRatePlan.setSupplier(product.getSupplierId());
            partnerShareRatePlan.setProduct(product.getId());

            return partnerShareRatePlan;

        }).collect(Collectors.toList()));

        ratePlan.setBillingPeriods(EnumBillingPeriod.Month);
        ratePlan.setChargeModel(EnumChargeModel.Perunit);
        ratePlan.setListPrice(ratePlanReq.getListPrice());
        ratePlan.setListPriceBase(EnumListPriceBase.BillingPeriod);
        ratePlan.setBillingDay(EnumBillingCycleDayType.SpecificDayOfMonth);
        ratePlan.setBillCycleDay(ratePlanReq.getBillCycleDay());
        ratePlan.setProduct(product.getId());
        ratePlan.setSupplier(product.getSupplierId());
        ratePlan.setCommissionType(ratePlanReq.getCommissionType());
        ratePlanRepository.save(ratePlan);
        return ratePlan;
    }



    public RatePlan addComponentRight(Subscription subscription, ComponentRight component) {

        RatePlan ratePlan = new RatePlan();
        ratePlan.setBillingPeriods(EnumBillingPeriod.Month);
        ratePlan.setChargeModel(EnumChargeModel.Perunit);
        ratePlan.setListPrice(10);
        ratePlan.setListPriceBase(EnumListPriceBase.BillingPeriod);
        ratePlan.setBillingDay(EnumBillingCycleDayType.SpecificDayOfMonth);
        ratePlan.setBillCycleDay(1);

        ratePlan = ratePlanRepository.save(ratePlan);


        return ratePlan;
    }









    public Subscription create(Supplier supplier,RatePlan ratePlan,Product product, SubscriptionReq pojo) {




        LocalDateTime original_billing_date = LocalDateTime.now();
        LocalDateTime billing_cycle  = null;
        if(ratePlan.getBillingPeriods().equals(EnumBillingPeriod.Quarter)){
            original_billing_date = original_billing_date.with( TemporalAdjusters.firstDayOfNextMonth() ) ;

            billing_cycle  = original_billing_date.plusDays(1);

        }
        if(ratePlan.getBillingPeriods().equals(EnumBillingPeriod.Week)){

            original_billing_date = original_billing_date.with(DayOfWeek.MONDAY);
            original_billing_date = original_billing_date.plusDays(ratePlan.getBillCycleDay());
            billing_cycle  = original_billing_date.plusWeeks(1);

        }
        if(ratePlan.getBillingPeriods().equals(EnumBillingPeriod.Month)){
            original_billing_date = original_billing_date.with( TemporalAdjusters.firstDayOfMonth() ) ;
            original_billing_date = original_billing_date.plusDays(ratePlan.getBillCycleDay());
            billing_cycle  = original_billing_date.plusMonths(1);
        }




        Subscription subscription = new Subscription();
        subscription.setSupplier(supplier.getId());
        subscription.setDescription(pojo.getDescription());
        subscription.setCode(idGenService.subscriptionCode());
        subscription.setName(pojo.getName());
        subscription.setCurrent_period_end(billing_cycle);
        subscription.setCurrent_period_start(original_billing_date);

        subscription.setBilling_cycle_anchor(billing_cycle);

        subscription.setTermSetting(pojo.getTermSetting());

        subscription.setRatePlan(ratePlan.getId());
        subscription.setProduct(product.getId());

        subscription.setCustomer(product.getSupplierId());



        // attraction.set(pojo.getName());

        //   attraction.setLongdesc(pojo.getLongdesc());
        //  attraction.setShortdesc(pojo.getShortdesc());
        //  attraction.setVideo(pojo.getVideo());
        subscription.setStatus(EnumSubscriptionStatus.draft);
        //  attraction.setThumbnail_image(pojo.getVideo_url());
        subscription = subscriptionRepository.save(subscription);


        List<Component> componentRights = componentRepository.findAllByProduct(product.getId());

        Subscription finalSubscription = subscription;
        componentRights.stream().map(e->{
            ComponentRight componentRight = componentRightRepository.findById(e.getComponentRightId()).get();

            ComponentRightPojo createComponentRight = new ComponentRightPojo();
            createComponentRight.setDuration(componentRight.getDuration());
            createComponentRight.setName(componentRight.getName());
            createComponentRight.setPrivacy_level(EnumPrivacyLevel.private_);
            createComponentRight.setLong_desc(componentRight.getLong_desc());
            createComponentRight.setName_long(componentRight.getName_long());
            createComponentRight.setNote(componentRight.getNote());
            createComponentRight.setDuration(componentRight.getDuration());
            componentRightService.createComponentRight(supplier,createComponentRight, finalSubscription,componentRight);
            return componentRight;
        }).collect(Collectors.toList());



        return subscription;
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
