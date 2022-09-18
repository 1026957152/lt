package com.lt.dom.rabbit;

import com.lt.dom.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.stereotype.Component;

@Component
public class ReturnCallbackService implements RabbitTemplate.ReturnCallback {
    private static final Logger logger = LoggerFactory.getLogger(ReturnCallbackService.class);


    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

        String lo = String.format("returnedMessage ===> replyCode=%s ,replyText=%s ,exchange=%s ,routingKey=%s", replyCode, replyText, exchange, routingKey);

        System.out.println(lo);

        logger.info("returnedMessage ===> replyCode={} ,replyText={} ,exchange={} ,routingKey={}", replyCode, replyText, exchange, routingKey);
    }


}

