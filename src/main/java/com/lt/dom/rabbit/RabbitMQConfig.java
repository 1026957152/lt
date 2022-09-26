package com.lt.dom.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration
public class RabbitMQConfig {

    public static final String queue_name = "testee";
    @Bean
    public Queue testeQueue() {
        return new Queue(queue_name, true);
    }


    @Bean
    DirectExchange exchange_direct() {
        return new DirectExchange("direct-exchange");
    }

    @Bean
    DirectExchange exchange_direct_fuck() {
        return ExchangeBuilder.directExchange(exchange_name).durable(true).build();
     //   return new DirectExchange("direct-exchange-fuck").durable(true).;
    }

    @Bean
    Binding testeBinding(DirectExchange exchange_direct,Queue testeQueue) {


        return BindingBuilder.bind(testeQueue).to(exchange_direct).with("teste-routing-key");
    }









    @Bean
    public Queue appSpecificQueue() {
        return new Queue(QUEUE_SPECIFIC_NAME, true);
    }

    @Bean
    DirectExchange exchange_appSpecificQueue() {
        return ExchangeBuilder.directExchange(EXCHANGE_NAME).durable(true).build();
        //   return new DirectExchange("direct-exchange-fuck").durable(true).;
    }

    @Bean
    Binding appSpecificQueueBinding(DirectExchange exchange_appSpecificQueue,Queue appSpecificQueue) {

        return BindingBuilder.bind(appSpecificQueue).to(exchange_appSpecificQueue).with(ROUTING_KEY);
    }
    public static final String exchange_name = "direct-exchange-fuck";

    public static final String topicExchangeName = "direct-exchange";

    public static final String routing_key = "teste-routing-key";

    public static final String EXCHANGE_NAME = "appExchange";

    public static final String QUEUE_SPECIFIC_NAME = "appSpecificQueue";
    public static final String ROUTING_KEY = "messages.key";

/*    @Bean
    Binding testeBinding(Queue testeQueue) {

        DirectExchange exchange = new DirectExchange(topicExchangeName);

        return BindingBuilder.bind(testeQueue).to(exchange).with(routing_key);
    }*/














}