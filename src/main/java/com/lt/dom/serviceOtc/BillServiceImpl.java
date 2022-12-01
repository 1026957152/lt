package com.lt.dom.serviceOtc;


import com.lt.dom.InvoiceItem;
import com.lt.dom.credit.EnumActionType;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BillRunReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl {

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
    IdGenServiceImpl idGenService;
    @Autowired
    HistoryServiceImpl historyService;

/*
    @Autowired
    SubscriptionServiceImpl subscriptionService;
*/


    public void run(){
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 2);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

// every night at 2am you run your task
        Timer timer = new Timer();
      //  timer.schedule(new YourTask(), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); // period: 1 day
    }

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
            List<Subscription> subscriptionList_ = subscriptionRepository.findAllById(billRun.getProcessingSubscriptions().stream().map(e->e.getSubscription()).collect(Collectors.toList()));
            Subscription subscription = subscriptionList_.get(0);

            Optional<RatePlan> optionalRatePlan = ratePlanRepository.findById(subscription.getRatePlan());
            RatePlan ratePlan = optionalRatePlan.get();
            processing(subscription,ratePlan,billRun.getTargetDate());
        }


        return billRun;
    }
    public Subscription generateNextBillDay(Subscription subscription,RatePlan ratePlan){



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
        subscription.setCurrent_period_end(billing_cycle);
        subscription.setCurrent_period_start(original_billing_date);

        subscription.setBilling_cycle_anchor(billing_cycle);
        subscription = subscriptionRepository.save(subscription);

        return subscription;
    }

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

    public void processing(Subscription subscription, RatePlan ratePlan, LocalDateTime targetDate) {





        List<Usage> usageList = usageRepository.findAll();


        subscription.setCustomer(1l);


        LocalDate startDate = subscription.getCurrent_period_start().toLocalDate();
        LocalDate endDate = subscription.getCurrent_period_end().toLocalDate();


        List<Usage> usages = usageList.stream()
                .filter(e->e.getCreatedDate().isBefore(targetDate))
                .collect(Collectors.toList());


        Double total = usages.stream().mapToDouble(e->{

            return ratePlan.getListPrice()*e.getQty();

        }).sum();
        Long qty = usages.stream().mapToLong(e->{

            return e.getQty();

        }).sum();

        Invoice invoice = new Invoice();


        invoice.setAutopay(true);
        invoice.setCode(idGenService.invoiceNo());
        invoice.setDate(LocalDate.now());
        invoice.setPaid(false);
        invoice.setStatus(EnumInvoiceStatus.draft);
        invoice.setSubscription(subscription.getId());
        InvoiceItem invoiceItem = new InvoiceItem();

        invoiceItem.setUnitCost(ratePlan.getListPrice());
        invoiceItem.setQuantity(qty.intValue());
        invoiceItem.setAmount(invoiceItem.getQuantity()*invoiceItem.getUnitCost());
        invoiceItem.setRatePlan(subscription.getRatePlan());
        invoiceItem.setInvoice(invoice);

        invoice.setItems(Arrays.asList(invoiceItem));
        invoice.setTotal(total.doubleValue());
        invoice.setSubTotal(total.doubleValue());
        invoice.setGeneratdOn(LocalDateTime.now());


        historyService.create(invoice.getId(), EnumActionType.invoice_created,"新建");

        invoiceRepository.save(invoice);

    }



    @Scheduled(fixedDelay = 500000)
    // @Scheduled(cron = "${erwin.cron:0/2 * * * * ?}")
    public void cronTaskYmlDemo() {
        List<BillRun> billRun = billRunRepository.findAll();
        billRun = billRun.stream()
                .filter(e->e.getType().equals(EnumTypesOfBillRuns.scheduled))
                .filter(e->e.getStatus().equals(EnumBillRunStatus.Processing))
                .collect(Collectors.toList());

        billRun.stream().forEach(ee->{
            List<Subscription> subscriptionList_ = subscriptionRepository.findAllById(ee.getProcessingSubscriptions().stream().map(e->e.getSubscription()).collect(Collectors.toList()));
            Subscription subscription = subscriptionList_.get(0);


            // 如果今天是结算日  ,先生产 账单， 然后 移动到下一个结算
            if(LocalDate.now().equals(subscription.getBilling_cycle_anchor().toLocalDate())){


                Optional<RatePlan> optionalRatePlan = ratePlanRepository.findById(subscription.getRatePlan());
                RatePlan ratePlan = optionalRatePlan.get();
                processing(subscription,ratePlan, ee.getTargetDate());


                generateNextBillDay(subscription,new RatePlan());
            }


        });

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


    public static void main(String[] a){
      //  -d "trial_end"=1610403705 \
      //  -d "billing_cycle_anchor"=1611008505
        Date trial_end = new Date(TimeUnit.SECONDS.toMillis(1610403705));
        System.out.println("trial_end"+trial_end);

        Date billing_cycle_anchor = new Date(TimeUnit.SECONDS.toMillis(1611008505));
        System.out.println("billing_cycle_anchor"+billing_cycle_anchor);
    }
}
