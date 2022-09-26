package com.lt.dom.controllerOct;

import com.lt.dom.rabbit.*;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/teste")
public class TesteController {

 //   @Autowired
    private QueueSender queueSender;
   // @Autowired
    private RabbitTemplate rabbitTemplate;

   // @Autowired
    private ConfirmCallbackService confirmCallbackService;

  //  @Autowired
    private ReturnCallbackService returnCallbackService;



    @GetMapping
    public String send(){
       // queueSender.send("test message");

        rabbitTemplate.convertAndSend(SenderConfig.topicExchangeName, "foo.bar.#","dddddddd");


        return "ok. done";
    }
    @GetMapping(value = "/direct")
    public String send_(){
        // queueSender.send("test message");



        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("zheaven123456");//id + 时间戳 （必须是全局唯一的）
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, RabbitMQConfig.routing_key,"dddddddddddddd",correlationData);


        return "ok. done";
    }

    @GetMapping(value = "/direct_noqueue")
    public String send_no_queue(){
        // queueSender.send("test message");



        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("zheaven123456");//id + 时间戳 （必须是全局唯一的）

        final var message = new CustomMessage("Hello there!", new Random().nextInt(50), false);

        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName, RabbitMQConfig.routing_key+"dd",message,correlationData);


        return "ok. done";
    }

    @GetMapping(value = "/direct_pojo")
    public String send_no_pojo(){
        // queueSender.send("test message");



        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("zheaven123456");//id + 时间戳 （必须是全局唯一的）

        final var message = new CustomMessage("Hello there!", new Random().nextInt(50), false);

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY,message,correlationData);


        return "ok. done";
    }
}