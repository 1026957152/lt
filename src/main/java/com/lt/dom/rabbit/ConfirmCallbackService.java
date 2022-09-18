package com.lt.dom.rabbit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConfirmCallbackService implements RabbitTemplate.ConfirmCallback {
    private static final Logger logger = LoggerFactory.getLogger(ConfirmCallbackService.class);

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {

        if (!ack) {
            logger.error("消息发送异常!");
            System.out.println("消息发送异常!");
        } else {

            String lo = String.format("发送者爸爸已经收到确认，correlationData=%s ,ack=%s, cause=%s", correlationData.getId(), ack, cause);

        System.out.println(lo);
            logger.info("发送者爸爸已经收到确认，correlationData={} ,ack={}, cause={}", correlationData.getId(), ack, cause);
        }
    }
}

