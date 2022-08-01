package com.lt.dom.notification;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class EventHandler {

//  @Autowired
//  private EventBus eventBus;

    @Autowired
    private EventBus asyncEventBus;

    @Autowired
    private EventListener eventListener;

//  @PostConstruct
//  public void init() {
//      eventBus.register(eventListener);
//  }
//
//  @PreDestroy
//  public void destroy() {
//      eventBus.unregister(eventListener);
//  }

    @PostConstruct
    public void init() {
        asyncEventBus.register(eventListener);
    }

    @PreDestroy
    public void destroy() {
        asyncEventBus.unregister(eventListener);
    }


/*    order.canceled
    order.created
    order.updated*/

    public void order_paid(OrderPaidVo expBucketUserVo){
//      eventBus.post(expBucketUserVo);
        asyncEventBus.post(expBucketUserVo);
        //log.info("eventBus post event {}",expBucketUserVo);
    }



    public void eventPost(NotificationData expBucketUserVo){
//      eventBus.post(expBucketUserVo);
        asyncEventBus.post(expBucketUserVo);
        //log.info("eventBus post event {}",expBucketUserVo);
    }

/*
    public void eventPost(ExperimentBucketUserCountVo experimentBucketUserCountVo){
//      eventBus.post(experimentBucketUserCountVo);
        asyncEventBus.post(experimentBucketUserCountVo);
       // log.info("eventBus post event {}",experimentBucketUserCountVo);
    }
*/








    public void voucher_published(VouncherPublishedVo expBucketUserVo){
//      eventBus.post(expBucketUserVo);
        asyncEventBus.post(expBucketUserVo);
        //log.info("eventBus post event {}",expBucketUserVo);
    }
}